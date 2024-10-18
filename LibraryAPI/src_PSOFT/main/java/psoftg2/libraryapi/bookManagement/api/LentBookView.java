package psoftg2.libraryapi.bookManagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import psoftg2.libraryapi.bookManagement.model.BookAuthor;
import psoftg2.libraryapi.bookManagement.model.Genre;

import java.util.List;

@Data
@Schema(description = "LentBookView")
public class LentBookView {
    private int lentCount;
    private Long id;
    private String isbn;
    private String title;
    private BookGenreView genre;
    private String description;
    private List<BookAuthorView> bookAuthors;
}

