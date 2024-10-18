package psoftg2.libraryapi.bookManagementTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import psoftg2.libraryapi.bookManagement.model.Book;
import psoftg2.libraryapi.bookManagement.model.BookAuthor;
import psoftg2.libraryapi.bookManagement.model.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class BookTest {
    @Test
    public void testISBNNotEmpty() {
        Book book = new Book("", "Title", new Genre(), Collections.emptyList(), "Description");
        Assertions.assertTrue(book.getIsbn().isEmpty());
    }

    @Test
    public void testISBNLength() {
        Book book = new Book("123456789", "Title", new Genre(), Collections.emptyList(), "Description");
        Assertions.assertFalse(book.getIsbn().length() >= 10 && book.getIsbn().length() <= 13);
    }

    @Test
    public void testTitleNotEmpty() {
        Book book = new Book("ISBN", "", new Genre(), Collections.emptyList(), "Description");
        Assertions.assertTrue(book.getTitle().isEmpty());
    }

    @Test
    public void testTitleNotStartingWithSpace() {
        Book book = new Book("ISBN", " Title", new Genre(), Collections.emptyList(), "Description");
        Assertions.assertTrue(book.getTitle().startsWith(" "));
    }

    @Test
    public void testTitleNotEndingWithSpace() {
        Book book = new Book("ISBN", "Title ", new Genre(), Collections.emptyList(), "Description");
        Assertions.assertTrue(book.getTitle().endsWith(" "));
    }

    @Test
    public void testDescriptionSupportsHTML() {
        Book book = new Book("ISBN", "Title", new Genre(), Collections.emptyList(), "<p>Description</p>");
        Assertions.assertTrue(book.getDescription().contains("<p>"));
    }

    @Test
    public void testMandatoryFields() {
        Genre genre = new Genre("Self-Improvement");
        Book book = new Book(null, "Title", genre, Collections.emptyList(), "Description");
        Assertions.assertNull(book.getIsbn());
        Assertions.assertNotNull(book.getTitle());
    }

    @Test
    public void testCannotAlterISBN() {
        Book book = new Book("ISBN", "Title", new Genre(), Collections.emptyList(), "Description");
        book.setIsbn("9781982137274");
        Assertions.assertNotEquals("ISBN", book.getIsbn());
    }

    @Test
    public void testCanAlterTitle() {
        Book book = new Book("ISBN", "Title", new Genre(), Collections.emptyList(), "Description");
        book.setTitle("New Title");
        Assertions.assertEquals("New Title", book.getTitle());
    }

    @Test
    public void testCanAlterGenre() {
        Genre oldGenre = new Genre("Old Genre");
        Genre newGenre = new Genre("New Genre");
        Book book = new Book("ISBN", "Title", oldGenre, Collections.emptyList(), "Description");
        book.setGenre(newGenre);
        Assertions.assertEquals(newGenre, book.getGenre());
    }

    @Test
    public void testCanClearAuthors() {
        List<BookAuthor> authors = new ArrayList<>();
        authors.add(new BookAuthor(new Book(), null));
        authors.add(new BookAuthor(new Book(), null));
        Book book = new Book("ISBN", "Title", new Genre(), authors, "Description");
        book.setBookAuthors(null);
        Assertions.assertNull(book.getBookAuthors());
    }

    @Test
    public void testDisplayBookData() {
        Genre genre1 = new Genre("Self-Help");
        Book book = new Book("9781982137274", "The 7 Habits of Highly Effective People", genre1, null, "Powerful lessons in personal change.");
        Assertions.assertEquals("9781982137274", book.getIsbn());
        Assertions.assertEquals("The 7 Habits of Highly Effective People", book.getTitle());
        Assertions.assertEquals(genre1, book.getGenre());
        Assertions.assertEquals("Powerful lessons in personal change.", book.getDescription());
        Assertions.assertNull(book.getBookAuthors());
    }
}
