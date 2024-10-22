package psoftg2.libraryapi.lendingManagement.api;

public class LentBookView {
    private Long bookId;
    private Long lendCount; // Se você precisar também do número de empréstimos

    // Construtor
    public LentBookView(Long bookId, Long lendCount) {
        this.bookId = bookId;
        this.lendCount = lendCount;
    }

    // Getters e Setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getLendCount() {
        return lendCount;
    }

    public void setLendCount(Long lendCount) {
        this.lendCount = lendCount;
    }
}
