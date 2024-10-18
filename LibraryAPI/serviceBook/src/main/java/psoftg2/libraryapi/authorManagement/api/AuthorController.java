package psoftg2.libraryapi.authorManagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import psoftg2.libraryapi.authorManagement.model.Author;
import psoftg2.libraryapi.authorManagement.model.AuthorPhoto;
import psoftg2.libraryapi.authorManagement.services.AuthorServiceImpl;
import psoftg2.libraryapi.authorManagement.services.EditAuthorRequest;
import psoftg2.libraryapi.bookManagement.api.BookView;
import psoftg2.libraryapi.bookManagement.api.BookViewMapper;
import psoftg2.libraryapi.bookManagement.model.Book;
import psoftg2.libraryapi.bookManagement.model.BookAuthor;
import psoftg2.libraryapi.bookManagement.services.BookServiceImpl;
import psoftg2.libraryapi.exceptions.NotFoundException;
import psoftg2.libraryapi.fileStorage.UploadFileResponse;
import psoftg2.libraryapi.client.AuthServiceClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Authors", description = "Endpoints for managing Authors")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/authors")
public class AuthorController {

    private static final String IF_MATCH = "If-Match";
    private final AuthorServiceImpl authorService;
    private final BookServiceImpl bookService;
    private final AuthorViewMapper authorViewMapper;
    //private final LendingServiceImpl lendingService;
    private final BookViewMapper bookViewMapper;
    private final AuthorLentsViewMapper authorLentsViewMapper;
    private final AuthServiceClient authServiceClient;

    private boolean hasPermission(List<String> roles, String... allowedRoles) {
        for (String role : allowedRoles) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    @Operation(summary = "Gets all Authors")
    @GetMapping
    public List<AuthorView> getAuthors(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "100", required = false) int size,
            @RequestHeader("Authorization") String authorization) {

       /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = authorService.getAuthors(pageable);
        return authorPage.map(authorViewMapper::toAuthorView).getContent();
    }

    @Operation(summary = "Gets a specific Author by id")
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorView> getAuthor(@PathVariable("authorId") Long id, @RequestHeader("Authorization") String authorization) {

        /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        final var author = authorService.getAuthorsById(id).orElseThrow(() -> new NotFoundException(Author.class, id));
        return ResponseEntity.ok().eTag(Long.toString(author.getVersion())).body(authorViewMapper.toAuthorView(author));
    }

    @Operation(summary = "Gets a specific Author by name")
    @GetMapping("/name")
    public Iterable<AuthorView> getAuthors(@RequestParam String name, @RequestHeader("Authorization") String authorization) {

        /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        return authorViewMapper.toAuthorView(authorService.getAuthorsByName(name));
    }

    @Operation(summary = "Gets the co-authors of an author and their respective books")
    @GetMapping("/{authorId}/co-authors")
    public List<BookView> getAuthorCoAuthors(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "100", required = false) int size,
            @PathVariable("authorId") Long authorId,
            @RequestHeader("Authorization") String authorization) {

        /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        Pageable pageable = PageRequest.of(page, size);
        List<BookAuthor> bookAuthors = bookService.getBookAuthorsByAuthorId(authorId);
        List<Book> booksList = new ArrayList<>();

        for (BookAuthor bookAuthor : bookAuthors) {
            if (bookAuthor.getBook().getBookAuthors().size() > 1) {
                booksList.add(bookAuthor.getBook());
            }
        }

        int start = Math.min((int) pageable.getOffset(), booksList.size());
        int end = Math.min((start + pageable.getPageSize()), booksList.size());
        List<Book> paginatedBooks = booksList.subList(start, end);

        Page<Book> booksPage = new PageImpl<>(paginatedBooks, pageable, booksList.size());
        return booksPage.map(bookViewMapper::toBookView).getContent();
    }
    /*
    @Operation(summary = "Gets the top-5 authors")
    @GetMapping("/top-authors")
    @RolesAllowed({Role.LIBRARIAN, Role.ADMIN, Role.READER})
    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = AuthorView.class))) })
    public Iterable<AuthorLentsView> getTop5Authors() {
        List<Author> allAuthors = new ArrayList<>();
        int totalPages = authorService.getTotalPages();
        for (int i = 0; i < totalPages; i++) {
            Pageable pageable = PageRequest.of(i, 5);
            Page<Author> authorsPage = authorService.getAuthors(pageable);
            allAuthors.addAll(authorsPage.getContent());
        }
        for (Author author : allAuthors) {
            List<BookAuthor> bookAuthors = bookService.getBookAuthorsByAuthorId(author.getId());
            int totalLents = 0;
            for (BookAuthor bookAuthor : bookAuthors) {
                List<Lending> lending = lendingService.getLentBook(bookAuthor.getId());
                totalLents += lending.size();
            }
            author.setLents(totalLents);
        }
        allAuthors.sort((a1, a2) -> Integer.compare(a2.getLents(), a1.getLents()));
        return authorLentsViewMapper.toAuthorLentsView(allAuthors.subList(0, Math.min(5, allAuthors.size())));
    }

     */

    @Operation(summary = "Gets the books from a specific Author by its id")
    @GetMapping("/{authorId}/books")
    public List<BookView> getAuthorBooks(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "100", required = false) int size,
            @PathVariable("authorId") Long authorId,
            @RequestHeader("Authorization") String authorization) {

        /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        Pageable pageable = PageRequest.of(page, size);
        List<BookAuthor> bookAuthors = bookService.getBookAuthorsByAuthorId(authorId);
        List<Book> booksList = new ArrayList<>();

        for (BookAuthor bookAuthor : bookAuthors) {
            booksList.add(bookAuthor.getBook());
        }

        int start = Math.min((int) pageable.getOffset(), booksList.size());
        int end = Math.min((start + pageable.getPageSize()), booksList.size());
        List<Book> paginatedBooks = booksList.subList(start, end);

        Page<Book> booksPage = new PageImpl<>(paginatedBooks, pageable, booksList.size());
        return booksPage.map(bookViewMapper::toBookView).getContent();
    }


    @Operation(summary = "Downloads a photo of an author by id")
    @GetMapping("/{authorId}/photo")
    public ResponseEntity<Resource> getBookCover(@PathVariable("authorId") final String authorId, @RequestHeader("Authorization") String authorization) {
/*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        AuthorPhoto authorPhoto = authorService.getAuthorPhoto(authorId);
        final Resource resource = new ByteArrayResource(authorPhoto.getImage());
        String contentType = authorPhoto.getContentType();
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "Creates a new Author")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorView> createAuthor(@Valid @RequestPart("author") final EditAuthorRequest resource,
                                                   @RequestPart(value = "authorPhoto", required = false) MultipartFile authorPhoto,
                                                   @RequestHeader("Authorization") String authorization) {

        /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        final var author = authorService.createAuthor(resource, authorPhoto);
        final var newbarUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(author.getId().toString())
                .build().toUri();

        return ResponseEntity.created(newbarUri).eTag(Long.toString(author.getVersion()))
                .body(authorViewMapper.toAuthorView(author));
    }
    @Operation(summary = "Uploads a author photo")
    @PostMapping("/{authorId}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UploadFileResponse> uploadFile(@PathVariable("authorId") final String authorId,
                                                         @RequestParam("file") final MultipartFile file,
                                                         @RequestHeader("Authorization") String authorization) throws URISyntaxException {

       /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        final UploadFileResponse up = authorService.doUploadFile(authorId, file);
        return ResponseEntity.created(new URI(up.getFileDownloadUri())).body(up);
    }

    @Operation(summary = "Fully replaces an existing author.")
    @PutMapping(path = "{authorId}")
    public ResponseEntity<AuthorView> updateAuthor(final WebRequest request,
                                                   @PathVariable("authorId") Long id,
                                                   @Valid @RequestBody final EditAuthorRequest resource,
                                                   @RequestHeader("Authorization") String authorization) {

       /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Author author = authorService.updateAuthor(id, resource, getVersionFromIfMatchHeader(ifMatchValue));
        return ResponseEntity.ok().eTag(Long.toString(author.getVersion())).body(authorViewMapper.toAuthorView(author));
    }

    @Operation(summary = "Partially updates an existing author")
    @PatchMapping(path = "{authorId}")
    public ResponseEntity<AuthorView> partialUpdateAuthor(final WebRequest request,
                                                          @PathVariable("authorId") Long id,
                                                          @Valid @RequestBody final EditAuthorRequest resource,
                                                          @RequestHeader("Authorization") String authorization) {

        /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Author author = authorService.partialUpdateAuthor(id, resource, getVersionFromIfMatchHeader(ifMatchValue));
        return ResponseEntity.ok().eTag(Long.toString(author.getVersion())).body(authorViewMapper.toAuthorView(author));
    }

    private Long getVersionFromIfMatchHeader(final String ifMatchHeader) {
        if (ifMatchHeader.startsWith("\"")) {
            return Long.parseLong(ifMatchHeader.substring(1, ifMatchHeader.length() - 1));
        }
        return Long.parseLong(ifMatchHeader);
    }
}