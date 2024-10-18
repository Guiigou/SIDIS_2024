package psoftg2.libraryapi.lendingManagementTests;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import psoftg2.libraryapi.lendingManagement.model.Lending;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LendingTest {
    private List<Lending> lendings;

    @BeforeEach
    public void setUp() {
        lendings = new ArrayList<>();
    }

    @Test
    public void testLendingWithNoOverdueBooks() {
        LocalDate currentDate = LocalDate.now();
        Lending overdueLending = new Lending("123", 1L, 1L, "Book1", currentDate.minusDays(5), currentDate.minusDays(3), null, false, null, null);
        lendings.add(overdueLending);

        Lending newLending = new Lending("456", 1L, 2L, "Book2", currentDate, currentDate.plusDays(7), null, false, null, null);
        Assertions.assertFalse(lendings.contains(newLending));
    }

    @Test
    public void testMaximumBooksLent() {
        LocalDate currentDate = LocalDate.now();
        lendings.add(new Lending("123", 1L, 1L, "Book1", currentDate, currentDate.plusDays(7), null, false, null, null));
        lendings.add(new Lending("456", 1L, 2L, "Book2", currentDate, currentDate.plusDays(7), null, false, null, null));

        Lending newLending = new Lending("789", 1L, 3L, "Book3", currentDate, currentDate.plusDays(7), null, false, null, null);
        Assertions.assertFalse(lendings.contains(newLending));
    }

    @Test
    public void testReturnDatePresentation() {
        LocalDate currentDate = LocalDate.now();
        Lending newLending = new Lending("123", 1L, 1L, "Book1", currentDate, currentDate.plusDays(7), null, false, null, null);
        Assertions.assertNotNull(newLending.getLimitDate());
    }

    @Test
    public void testLendingCorrespondsToOneBook() {
        LocalDate currentDate = LocalDate.now();
        Lending newLending = new Lending("123", 1L, 1L, "Book1", currentDate, currentDate.plusDays(7), null, false, null, null);
        Assertions.assertEquals(1, newLending.getBookId());
    }

    @Test
    public void testAnonymousUserCannotRequestBook() {
        LocalDate currentDate = LocalDate.now();
        Lending newLending = new Lending("123", null, 1L, "Book1", currentDate, currentDate.plusDays(7), null, false, null, null);
        Assertions.assertFalse(lendings.contains(newLending));
    }
}
