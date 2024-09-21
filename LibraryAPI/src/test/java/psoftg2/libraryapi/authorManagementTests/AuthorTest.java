package psoftg2.libraryapi.authorManagementTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import psoftg2.libraryapi.authorManagement.model.Author;

public class AuthorTest {

    @Test
    public void testAuthorNameNotBlank() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Author("  ", "Short Bio");
        });
        String expectedMessage = "Name can't be blank";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAuthorShortBioNotBlank() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Author("Author Name", "  ");
        });
        String expectedMessage = "Short Bio can't be blank";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAuthorNameNotNull() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Author(null, "Short Bio");
        });
        String expectedMessage = "Name can't be null";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAuthorShortBioNotNull() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Author("Author Name", null);
        });
        String expectedMessage = "Short Bio can't be null";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAuthorNameAlterable() {
        Author author = new Author("Author Name", "Short Bio");
        author.setName("New Name");
        Assertions.assertEquals("New Name", author.getName());
    }

    @Test
    public void testAuthorShortBioAlterable() {
        Author author = new Author("Author Name", "Short Bio");
        author.setShortBio("New Short Bio");
        Assertions.assertEquals("New Short Bio", author.getShortBio());
    }

    @Test
    public void testAuthorNameTooLong() {
        String longName = "A".repeat(151);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Author(longName, "Short Bio");
        });
        String expectedMessage = "Name exceeds the limit of 150 characters";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAuthorShortBioTooLong() {
        String longBio = "B".repeat(4097);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Author("Author Name", longBio);
        });
        String expectedMessage = "Short Bio exceeds the limit of 4096 characters";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}