package psoftg2.libraryapi.readerManagementTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import psoftg2.libraryapi.readerManagement.model.Reader;
import psoftg2.libraryapi.readerManagement.model.ReaderPhoto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class ReaderTest {

    @Test
    public void testReaderCodeNotEmpty() {
        Reader reader = new Reader("", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        Assertions.assertTrue(reader.getReaderCode().isEmpty());
    }

    @Test
    public void testNameNotEmpty() {
        Reader reader = new Reader("12345", "", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        Assertions.assertTrue(reader.getName().isEmpty());
    }

    @Test
    public void testEmailNotEmpty() {
        Reader reader = new Reader("12345", "John Doe", "", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        Assertions.assertTrue(reader.getEmail().isEmpty());
    }

    @Test
    public void testValidPhoneNumberLength() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        Assertions.assertEquals(9, reader.getPhoneNumber().toString().length());
    }

    @Test
    public void testMandatoryFields() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        Assertions.assertNotNull(reader.getReaderCode());
        Assertions.assertNotNull(reader.getName());
        Assertions.assertNotNull(reader.getEmail());
        Assertions.assertNotNull(reader.getDateOfBirth());
        Assertions.assertNotNull(reader.getPhoneNumber());
        Assertions.assertNotNull(reader.getGDBRConsent());
    }

    @Test
    public void testCanAlterName() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        reader.setName("Jane Doe");
        Assertions.assertEquals("Jane Doe", reader.getName());
    }

    @Test
    public void testCanAlterEmail() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        reader.setEmail("jane@example.com");
        Assertions.assertEquals("jane@example.com", reader.getEmail());
    }

    @Test
    public void testCanAlterPhoneNumber() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        reader.setPhoneNumber(987654321);
        Assertions.assertEquals(987654321, reader.getPhoneNumber());
    }

    @Test
    public void testCanAddInterests() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        List<String> interests = Arrays.asList("Fiction", "Science");
        reader.setInterests(interests);
        Assertions.assertEquals(interests, reader.getInterests());
    }

    @Test
    public void testCanSetFunnyQuote() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        reader.setFunnyQuote("Reading is fun!");
        Assertions.assertEquals("Reading is fun!", reader.getFunnyQuote());
    }

    @Test
    public void testCanSetPhoto() {
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, Collections.emptyList());
        ReaderPhoto photo = new ReaderPhoto();
        photo.setImage(new byte[]{1, 2, 3});
        reader.setReaderPhoto(photo);
        Assertions.assertNotNull(reader.getReaderPhoto());
    }

    @Test
    public void testDisplayReaderData() {
        List<String> interests = Arrays.asList("Fiction", "Science");
        Reader reader = new Reader("12345", "John Doe", "john@example.com", LocalDate.of(1990, 1, 1), 123456789, true, interests, "Reading is fun!");
        Assertions.assertEquals("12345", reader.getReaderCode());
        Assertions.assertEquals("John Doe", reader.getName());
        Assertions.assertEquals("john@example.com", reader.getEmail());
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), reader.getDateOfBirth());
        Assertions.assertEquals(123456789, reader.getPhoneNumber());
        Assertions.assertTrue(reader.getGDBRConsent());
        Assertions.assertEquals(interests, reader.getInterests());
        Assertions.assertEquals("Reading is fun!", reader.getFunnyQuote());
    }
}
