package psoftg2.libraryapi.lendingManagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import psoftg2.libraryapi.client.AuthServiceClient;
import psoftg2.libraryapi.lendingManagement.model.Lending;
import psoftg2.libraryapi.lendingManagement.services.CreateLendingRequest;
import psoftg2.libraryapi.lendingManagement.services.EditLendingRequest;
import psoftg2.libraryapi.lendingManagement.services.LendingServiceImpl;
import psoftg2.libraryapi.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Lendings", description = "Endpoints for managing Lendings")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/lendings")
public class LendingController {

    private static final String IF_MATCH = "If-Match";
    private final LendingServiceImpl lendingService;
    private final LendingViewMapper lendingViewMapper;
    private final AuthServiceClient authServiceClient;

    private boolean hasPermission(List<String> roles, String... allowedRoles) {
        for (String role : allowedRoles) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    @Operation(summary = "Gets all Lendings")
    @GetMapping
    @ApiResponse(description = "Success", responseCode = "200", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LendingView.class)))})
    public List<LendingView> getLendingsPage(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "100", required = false) int size,
            @RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Check permissions
        List<String> roles = authServiceClient.getUserRoles(token);
        if (!hasPermission(roles, "LIBRARIAN", "ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Lending> lendingsPage = lendingService.getLendings(pageable);
        return lendingsPage.map(lendingViewMapper::toLendingView).getContent();
    }

    @Operation(summary = "Gets a specific Lending")
    @GetMapping("/{lendingId}")
    public ResponseEntity<LendingView> getLending(@PathVariable("lendingId") Long id,
                                                  @RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Check permissions
        List<String> roles = authServiceClient.getUserRoles(token);
        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        final var lending = lendingService.getLending(id).orElseThrow(() -> new NotFoundException(Lending.class, id));

        return ResponseEntity.ok().eTag(Long.toString(lending.getVersion())).body(lendingViewMapper.toLendingView(lending));
    }

    @Operation(summary = "Gets a list of overdue lending sorted by their tardiness")
    @GetMapping("/overdue")
    public List<LendingView> getOverdue(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "100", required = false) int size,
            @RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Check permissions
        List<String> roles = authServiceClient.getUserRoles(token);
        if (!hasPermission(roles, "LIBRARIAN", "ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Lending> overdueLendingsPage = lendingService.getOverdueLendings(pageable);
        return overdueLendingsPage.map(lendingViewMapper::toLendingView).getContent();
    }

    @Operation(summary = "Gets average lending duration")
    @GetMapping("/average")
    public double getAverageLendingDuration(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Check permissions
        List<String> roles = authServiceClient.getUserRoles(token);
        if (!hasPermission(roles, "LIBRARIAN", "ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return lendingService.getAverageLendingDuration();
    }

    /*
    @Operation(summary = "Gets the number of lendings per month for the last 12 months, broken down by genre")
    @GetMapping("/lending-genre/{genreId}")
    public Map<Integer, Long> getAveragePerGenreInMonth(@PathVariable("genreId") Long genreId) {
        final var genre = genreService.getGenreById(genreId).orElseThrow(() -> new NotFoundException(Author.class, genreId));
        return lendingService.numberOfLendingsPerMonthByGenre(genre);
    }

    @Operation(summary = "Gets the average number of lending per genre of a certain month\n")
    @GetMapping("/average-per-genre/{date}")
    public double getAveragePerGenreInMonth(@PathVariable("date") LocalDate date) {
        int numberOfGenres = genreService.getGenres().size();
        return lendingService.AveragePerGenreInMonth(date, numberOfGenres);
    }

     */

    @Operation(summary = "Gets average lending duration per book")
    @GetMapping("/average-per-book")
    public Iterable<LendingAvgPerBookView> getAverageLendingDurationPerBook(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Check permissions
        List<String> roles = authServiceClient.getUserRoles(token);
        if (!hasPermission(roles, "LIBRARIAN", "ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return lendingService.getAverageLendingDurationPerBook();
    }
 /*
    @Operation(summary = "Gets average lending duration per genre per month for a certain period")
    @GetMapping("/average-duration-per-genre")
    public Iterable<LendingAvgPerGenrePerMonthView> getAverageLendingDurationPerGenrePerMonth(
            @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        return lendingService.getAverageLendingDurationPerGenrePerMonth(startDate, endDate);
    }


    @Operation(summary = "Creates a new Lending")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LendingView> createLending(@Valid @RequestBody final CreateLendingRequest resource) {
        Lending lending = lendingService.createLending(resource);

        final var newbarUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(lending.getId().toString())
                .build().toUri();

        return ResponseEntity.created(newbarUri).eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }

     */

    @Operation(summary = "Return a Book")
    @PostMapping("/return")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LendingView> returnBook(@Valid @RequestBody final EditLendingRequest resource,
                                                  @RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", ""); // Token from header

        // Check permissions
        List<String> roles = authServiceClient.getUserRoles(token);
        if (!hasPermission(roles, "LIBRARIAN", "ADMIN", "READER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Lending lending = lendingService.returnBook(resource);

        final var newbarUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(lending.getId().toString())
                .build().toUri();

        return ResponseEntity.created(newbarUri).eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }
    
    private Long getVersionFromIfMatchHeader(final String ifMatchHeader) {

        if (ifMatchHeader.startsWith("\"")) {
            return Long.parseLong(ifMatchHeader.substring(1, ifMatchHeader.length() - 1));
        }
        return Long.parseLong(ifMatchHeader);
    }


    //Backend Endpoints
    @GetMapping("/top-books")
    public ResponseEntity<List<LentBookView>> getTopBooks() {

        List<LentBookView> topBooks = lendingService.getTopBooks();
        return ResponseEntity.ok(topBooks);
    }

    @GetMapping("/top-readers")
    public ResponseEntity<List<LendingReaderView>> getTopReaders() {

        List<LendingReaderView> topReaders = lendingService.getTopReaders();
        return ResponseEntity.ok(topReaders);
    }
}


