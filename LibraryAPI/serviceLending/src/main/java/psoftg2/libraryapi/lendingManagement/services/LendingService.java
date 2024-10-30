package psoftg2.libraryapi.lendingManagement.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import psoftg2.libraryapi.lendingManagement.api.LendingAvgPerBookView;
import psoftg2.libraryapi.lendingManagement.api.LendingAvgPerGenrePerMonthView;
import psoftg2.libraryapi.lendingManagement.api.LendingReaderView;
import psoftg2.libraryapi.lendingManagement.api.LentBookView;
import psoftg2.libraryapi.lendingManagement.model.Lending;
import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.util.Optional;

public interface LendingService {
    Optional<Lending> getLending(Long lendingId);
    Page<Lending> getLendings(Pageable pageable);
    Iterable<Lending> getAllLendings();
    List<Lending> getLentBook(Long bookId);
    Page<Lending> getOverdueLendings(Pageable pageable);
    double getAverageLendingDuration();
    //double AveragePerGenreInMonth(LocalDate date, int numberOfGenres);
    //Map<Integer, Long> numberOfLendingsPerMonthByGenre(Genre genre);
    Iterable<LendingAvgPerBookView> getAverageLendingDurationPerBook();
    //Iterable<LendingAvgPerGenrePerMonthView> getAverageLendingDurationPerGenrePerMonth(LocalDate startDate, LocalDate endDate);
    //Lending createLending(CreateLendingRequest resource);
    Lending returnBook(EditLendingRequest resource);

    List<LentBookView> getTopBooks();
    List<LendingReaderView> getTopReaders();
}
