package psoftg2.libraryapi.readerManagement.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import psoftg2.libraryapi.fileStorage.UploadFileResponse;
import psoftg2.libraryapi.readerManagement.model.Reader;
import psoftg2.libraryapi.readerManagement.model.ReaderPhoto;

import java.time.LocalDate;
import java.util.Optional;

public interface ReaderService {
    Page<Reader> getReaders(Pageable pageable);
    Iterable<Reader> getAllReaders();
    //Iterable<Reader> getTopReaders(int topN);
    //Iterable<Reader> getTopReadersperGenre(int topN, Genre genre, LocalDate startDate, LocalDate endDate);
    Optional<Reader> getReaderByIdWithQuote(Long id);
    //int getMonthlyLending(Long readerId, LocalDate startDate, LocalDate endDate);
    //Page<Book> getSuggestedBooks(Long readerId, Pageable pageable);
    Page<Reader> getReadersByPhoneNumberAndEmail(final String phoneNumber, final String email, Pageable pageable);
    Page<Reader> getReadersByPhoneNumber(final String phoneNumber, Pageable pageable);
    Page<Reader> getReadersByEmail(final String email, Pageable pageable);
    ReaderPhoto getReaderPhoto(final String readerId);
    Reader createReader(final EditReaderRequest resource, MultipartFile photo);
    Reader updateReader(final Long id, final EditReaderRequest resource, final long desiredVersion);
    Reader partialUpdateReader(final Long id, final EditReaderRequest resource, final long desiredVersion);
    UploadFileResponse doUploadFile(final String id, final MultipartFile file);
}
