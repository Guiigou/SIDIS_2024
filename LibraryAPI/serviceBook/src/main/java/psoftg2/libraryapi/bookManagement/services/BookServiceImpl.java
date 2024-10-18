package psoftg2.libraryapi.bookManagement.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import psoftg2.libraryapi.authorManagement.model.Author;
import psoftg2.libraryapi.bookManagement.model.BookAuthor;
import psoftg2.libraryapi.authorManagement.repository.AuthorRepository;
import psoftg2.libraryapi.bookManagement.model.Book;
import org.apache.commons.lang3.StringUtils;
import psoftg2.libraryapi.bookManagement.model.BookCover;
import psoftg2.libraryapi.bookManagement.model.Genre;
import psoftg2.libraryapi.bookManagement.repositories.BookAuthorRepository;
import psoftg2.libraryapi.bookManagement.repositories.BookCoverRepository;
import psoftg2.libraryapi.bookManagement.repositories.BookRepository;
import psoftg2.libraryapi.bookManagement.repositories.GenreRepository;
import psoftg2.libraryapi.bookManagement.util.BookUtil;
import psoftg2.libraryapi.exceptions.NotFoundException;
import psoftg2.libraryapi.fileStorage.FileStorageService;
import psoftg2.libraryapi.fileStorage.UploadFileResponse;
import org.springframework.data.domain.PageImpl;
//import psoftg2.libraryapi.lendingManagement.repositories.LendingRepository;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static psoftg2.libraryapi.bookManagement.util.BookUtil.*;

@Service
public class BookServiceImpl implements BookService{
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    //private final LendingRepository lendingRepository;
    private final BookCoverRepository bookCoverRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;
    private final EditBookMapper editBookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, /*LendingRepository lendingRepository,*/ BookCoverRepository bookCoverRepository, AuthorRepository authorRepository, BookAuthorRepository bookAuthorRepository, EditBookMapper editBookMapper, GenreRepository genreRepository, FileStorageService fileStorageService, RestTemplate restTemplate) {
        this.bookRepository = bookRepository;
        //this.lendingRepository = lendingRepository;
        this.bookCoverRepository = bookCoverRepository;
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.editBookMapper = editBookMapper;
        this.genreRepository = genreRepository;
        this.fileStorageService =  fileStorageService;
        this.restTemplate = restTemplate;
    }

    public Optional<Book> getBook(final String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Genre> getTopGenres() {
        return bookRepository.findTopGenres();
    }

    /*
    public Iterable<Book> getTopBooks() {
        return lendingRepository.findTopBookIds().stream()
                .map(record -> (Long) record[0])
                .map(bookId -> bookRepository.findById(bookId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

     */

    public Page<Book> getBooksByGenre(final String genre, Pageable pageable) {
        List<Book> filteredBooks = bookRepository.findAll().stream()
                .filter(book -> book.getGenre().getName().toLowerCase().contains(genre.toLowerCase()))
                .collect(Collectors.toList());
        return toPage(filteredBooks, pageable);
    }

    public Page<Book> getBooksByTitle(final String title, Pageable pageable) {
        List<Book> filteredBooks = bookRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        return toPage(filteredBooks, pageable);
    }

    public Page<Book> getBooksByAuthor(final String author, Pageable pageable) {
        List<Book> filteredBooks = bookRepository.findAll().stream()
                .filter(book -> book.getBookAuthors().stream()
                        .anyMatch(bookAuthor -> bookAuthor.getAuthor().getName().equalsIgnoreCase(author)))
                .collect(Collectors.toList());
        return toPage(filteredBooks, pageable);
    }

    public Page<Book> getBooksByTitleAndGenreAndAuthor(final String genre, final String title, final String author, Pageable pageable) {
        List<Book> filteredBooks = bookRepository.findAll().stream()
                .filter(book -> book.getGenre().getName().toLowerCase().contains(genre.toLowerCase()) &&
                        book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                        book.getBookAuthors().stream()
                                .anyMatch(bookAuthor -> bookAuthor.getAuthor().getName().equalsIgnoreCase(author)))
                .collect(Collectors.toList());
        return toPage(filteredBooks, pageable);
    }

    public List<BookAuthor> getBookAuthorsByAuthorId(Long authorId) {
        return bookAuthorRepository.getAuthorBooks(authorId);
    }

    public BookCover getBookCover(final String bookId) {
        final var existingBook = bookRepository.findById(Long.parseLong(bookId)).orElseThrow(() -> new NotFoundException("[ERROR] Book not found"));

        if (existingBook.getCover() == null) {
            throw new IllegalArgumentException("[ERROR] Book Cover not found with ID: " + existingBook.getId());
        }

        return existingBook.getCover();
    }

    public Book createBook(final CreateBookRequest resource, MultipartFile coverPhoto) {
        validateCreateBookRequest(resource);

        Book book = editBookMapper.create(resource);

        if (resource.getBookAuthors() != null) {
            updateBookAuthors(book, resource.getBookAuthors());
        }

        if (coverPhoto != null) {
            doUploadFile(book.getId().toString(), coverPhoto);
        }

        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(final Long id, final EditBookRequest resource, final long desiredVersion) {
        final var book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("[ERROR] Cannot update an object that does not yet exist"));

        final var existingGenre = genreRepository.findById(resource.getGenre().getId()).orElseThrow(() -> new NotFoundException("[ERROR] Genre not found"));

        book.updateData(desiredVersion, resource.getTitle(), existingGenre ,resource.getDescription());

        if (resource.getBookAuthors() != null) {
            updateBookAuthors(book, resource.getBookAuthors());
        }

        return bookRepository.save(book);
    }

    public Book partialUpdateBook(final Long id, final EditBookRequest resource, final long desiredVersion) {
        final var book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("[ERROR] Cannot update an object that does not yet exist"));

        Genre existingGenre = null;

        if (resource.getGenre() != null) {
            existingGenre = genreRepository.findById(resource.getGenre().getId()).orElseThrow(() -> new NotFoundException("[ERROR] Genre not found"));
        }

        book.applyPatch(desiredVersion, resource.getTitle(), existingGenre, resource.getDescription());

        if (resource.getBookAuthors() != null) {
            updateBookAuthors(book, resource.getBookAuthors());
        }
        return bookRepository.save(book);
    }

    private void validateCreateBookRequest(final CreateBookRequest request) {
        String bookTitle = request.getTitle();
        String trimmedTitle = bookTitle.trim();

        if(!bookTitle.equals(trimmedTitle)) {
            throw new IllegalArgumentException("[ERROR] Book Title cannot start or end with spaces.");
        }

        if (!BookUtil.isValidISBN(request.getIsbn())) {
            throw new IllegalArgumentException("[ERROR] ISBN-10 or ISBN-13 invalid ISBN.");
        }

        if (bookRepository.findBookByIsbn(request.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("[ERROR] Book with that ISBN is already registered.");
        }

        if (StringUtils.isBlank(request.getTitle()) ||
                StringUtils.isWhitespace(Character.toString(request.getTitle().charAt(0))) ||
                StringUtils.isWhitespace(Character.toString(request.getTitle().charAt(request.getTitle().length() - 1)))) {
            throw new IllegalArgumentException("[ERROR] Book title is mandatory and cannot start or end with spaces.");
        }

        if (StringUtils.isBlank(request.getGenre().getName()) || StringUtils.isBlank(request.getBookAuthors().toString())) {
            throw new IllegalArgumentException("[ERROR] Genre and author fields are mandatory.");
        }
    }

    public void updateBookAuthors(final Book book, final List<BookAuthor> bookAuthorsList) {
        //delete das entradas antigas da tabela para depois podermos introduzir os novos bookAuthors
        bookAuthorRepository.deleteByBookId(book.getId());

        //save no book uma vez que vai ser preciso para criar associacoes entre book e author na tabela bookAuthors
        bookRepository.save(book);

        if (bookAuthorsList != null && !bookAuthorsList.isEmpty()) {
            List<BookAuthor> listBookAuthors = new ArrayList<>();
            for (BookAuthor bookAuthor : bookAuthorsList) {
                Author author = bookAuthor.getAuthor();
                if (author != null && author.getName() != null && author.getShortBio() != null) {
                    Author existingAuthor = authorRepository.findAuthorByName(author.getName())
                            .orElseThrow(() -> new IllegalArgumentException("[ERROR] Author not found"));
                    listBookAuthors.add(new BookAuthor(book, existingAuthor));
                } else {
                    throw new IllegalArgumentException("[ERROR] Author information is incomplete. Please provide valid author details.");
                }
            }
            bookAuthorRepository.saveAll(listBookAuthors);
        }
    }

    public UploadFileResponse doUploadFile(final String id, final MultipartFile file) {

        if (isValidBookCover(file)) {
            BookCover cover = new BookCover();
            try {
                cover.setImage(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            cover.setContentType(file.getContentType());
            bookCoverRepository.save(cover);
            Book book = bookRepository.getById(Long.parseLong(id));
            book.setCover(cover);
            bookRepository.save(book);
        }

        final String fileName = fileStorageService.storeFile(id, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(fileName)
                .toUriString();

        fileDownloadUri = fileDownloadUri.replace("/covers/", "/cover/");

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    public static Page<Book> toPage(List<Book> books, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), books.size());
        List<Book> sublist = books.subList(start, end);
        return new PageImpl<>(sublist, pageable, books.size());
    }

    public List<String> getUserRolesFromAuthService(String username) {
        String authServiceUrl = "http://localhost:8081/api/auth/roles/" + username;

        ResponseEntity<String[]> response = restTemplate.getForEntity(authServiceUrl, String[].class);
        return Arrays.asList(response.getBody());
    }

}
