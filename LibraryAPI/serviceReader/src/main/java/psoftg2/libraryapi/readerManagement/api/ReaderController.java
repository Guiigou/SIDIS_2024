package psoftg2.libraryapi.readerManagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
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
import psoftg2.libraryapi.fileStorage.UploadFileResponse;
import psoftg2.libraryapi.readerManagement.model.Reader;
import psoftg2.libraryapi.readerManagement.model.ReaderPhoto;
import psoftg2.libraryapi.readerManagement.services.EditReaderRequest;
import psoftg2.libraryapi.readerManagement.services.ReaderServiceImpl;
import psoftg2.libraryapi.exceptions.NotFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Readers", description = "Endpoints for managing Readers")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/readers")
public class ReaderController {

    private static final String IF_MATCH = "If-Match";
    private final ReaderServiceImpl readerService;
    private final ReaderViewMapper readerViewMapper;
    private final ReaderProfileViewMapper readerProfileViewMapper ;
    private final ReaderLentsViewMapper readerLentsViewMapper;

    private boolean hasPermission(List<String> roles, String... allowedRoles) {
        for (String role : allowedRoles) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    @Operation(summary = "Gets a reader profile with a funny quote based on date of birth")
    @GetMapping("/{readerId}")
    @ApiResponse(description = "Success", content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReaderProfileView.class)) })
    public ResponseEntity<ReaderProfileView> getReaderProfileWithQuote(@PathVariable("readerId") Long id, @RequestHeader("Authorization") String authorization) {

        /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        var ReaderProfileView = readerProfileViewMapper.toReaderProfileView(readerService.getReaderByIdWithQuote(id).orElseThrow(() -> new NotFoundException(Reader.class, id)));
        return ResponseEntity.ok().body(ReaderProfileView);
    }

    @Operation(summary = "Gets Readers")
    @GetMapping
    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ReaderView.class))) })
    public Iterable<ReaderView> getReaders(@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                           @RequestParam(value = "email", required = false) String email,
                                           @RequestParam(value = "name", required = false) String name,
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
        Page<Reader> readersPage;

        if (name != null) {
            readersPage = readerService.getReadersByName(name, pageable);
        } else if (phoneNumber != null && email != null) {
            readersPage = readerService.getReadersByPhoneNumberAndEmail(phoneNumber, email, pageable);
        } else if (phoneNumber != null) {
            readersPage = readerService.getReadersByPhoneNumber(phoneNumber, pageable);
        } else if (email != null) {
            readersPage = readerService.getReadersByEmail(email, pageable);
        } else {
            readersPage = readerService.getReaders(pageable);
        }

        return  readersPage.map(readerViewMapper::toReaderView).getContent();
    }
/*
    @Operation(summary = "Gets the Top 5 Readers")
    @GetMapping("/top-readers")
    @ApiResponse(description = "Success", content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ReaderView.class))) })
    public Iterable<ReaderView> getTopReaders(@RequestHeader("Authorization") String authorization) {
         /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         *//*
        return readerViewMapper.toReaderView(readerService.getTopReaders(5));
    }
    */

    /*
    @Operation(summary = "Gets book suggestions based on reader's interest list")
    @GetMapping("/{readerId}/suggestions")
    public Iterable<BookView> getSuggestedBooks(@PathVariable("readerId") Long readerId,
                                                @RequestParam(defaultValue = "0", required = false) int page,
                                                @RequestParam(defaultValue = "100", required = false) int size, @RequestHeader("Authorization") String authorization) {
         /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
         *//*
        Pageable pageable = PageRequest.of(page, size);
        return bookViewMapper.toBookView(readerService.getSuggestedBooks(readerId, pageable));
    }*/

    /*
    @Operation(summary = "Gets the Top 5 Readers per genre of a certain period")
    @GetMapping("/top-readers/{genreId}")
    public Iterable<ReaderView> getTopReadersPerGenre(@PathVariable("genreId") Long genreId,
                                                      @RequestParam("startDate") final LocalDate startDate,
                                                      @RequestParam("endDate") final LocalDate endDate, @RequestHeader("Authorization") String authorization){

         /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */
        /*
        final var genre = genreService.getGenreById(genreId).orElseThrow(() -> new NotFoundException(Reader.class, genreId));
        return readerViewMapper.toReaderView(readerService.getTopReadersperGenre(5, genre, startDate, endDate));
    }
    */
    /*
    @Operation(summary = "Gets Monthly lending per reader of a certain period")
    @GetMapping("/monthly-lending")
    public Iterable<ReaderLentsView> getMonthlyLending(@RequestParam("startDate") final LocalDate startDate,
                                                       @RequestParam("endDate") final LocalDate endDate, @RequestHeader("Authorization") String authorization){
         /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */
/*
        Iterable<Reader> readers = readerService.getAllReaders();
        for (Reader reader : readers) {
            reader.setLents(readerService.getMonthlyLending(reader.getId(), startDate, endDate));
        }
        return readerLentsViewMapper.toReaderLentsView(readers);
    }
    */
    @Operation(summary = "Downloads a cover of a reader by id")
    @GetMapping("/{readerId}/photo")
    public ResponseEntity<Resource> getReaderPhoto(@PathVariable("readerId") final String readerId,
                                                 final HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

         /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        ReaderPhoto readerPhoto = readerService.getReaderPhoto(readerId);

        final Resource resource = new ByteArrayResource(readerPhoto.getImage());

        String contentType = readerPhoto.getContentType();

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "Creates a new Reader")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReaderView> createReader(@Valid @RequestPart("reader") final EditReaderRequest resource,
                                                   @RequestPart(value = "photo", required = false) MultipartFile photo, @RequestHeader("Authorization") String authorization) {

         /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        Reader reader = readerService.createReader(resource, photo);

        final var newbarUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(reader.getId().toString())
                .build().toUri();

        return ResponseEntity.created(newbarUri).eTag(Long.toString(reader.getVersion()))
                .body(readerViewMapper.toReaderView(reader));
    }

    @Operation(summary = "Uploads a cover of a Reader")
    @PostMapping("/{readerId}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UploadFileResponse> uploadFile(@PathVariable("readerId") final String readerId,
                                                         @RequestParam("file") final MultipartFile file, @RequestHeader("Authorization") String authorization) throws URISyntaxException {

         /*
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Roles from AuthService
        List<String> roles = authServiceClient.getUserRoles(token);

        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

         */

        final UploadFileResponse up = readerService.doUploadFile(readerId, file);

        return ResponseEntity.created(new URI(up.getFileDownloadUri())).body(up);

    }

    @Operation(summary = "Fully replaces an existing reader. If the specified id does not exist does nothing and returns 400.")
    @PutMapping(path = "{readerId}")
    public ResponseEntity<ReaderView> updateReader(final WebRequest request,
                                               @PathVariable("readerId") Long id,
                                               @Valid @RequestBody final EditReaderRequest resource, @RequestHeader("Authorization") String authorization) {

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
        Reader reader = readerService.updateReader(id, resource, getVersionFromIfMatchHeader(ifMatchValue));
        return ResponseEntity.ok().eTag(Long.toString(reader.getVersion())).body(readerViewMapper.toReaderView(reader));
    }

    @Operation(summary = "Partially updates an existing reader")
    @PatchMapping(path = "{readerId}")
    public ResponseEntity<ReaderView> partialUpdateReader(final WebRequest request,
                                                      @PathVariable("readerId") Long id,
                                                      @Valid @RequestBody final EditReaderRequest resource, @RequestHeader("Authorization") String authorization) {

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
        Reader reader = readerService.partialUpdateReader(id, resource, getVersionFromIfMatchHeader(ifMatchValue));
        return ResponseEntity.ok().eTag(Long.toString(reader.getVersion())).body(readerViewMapper.toReaderView(reader));
    }

    private Long getVersionFromIfMatchHeader(final String ifMatchHeader) {
        if (ifMatchHeader.startsWith("\"")) {
            return Long.parseLong(ifMatchHeader.substring(1, ifMatchHeader.length() - 1));
        }
        return Long.parseLong(ifMatchHeader);
    }

}