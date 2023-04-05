package springbox.basiccache;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/api/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping("/api/books")
    public Long saveBook(@RequestBody BookRequest bookRequest) {
        Book book = bookService.saveBook(bookRequest.getIsbn(), bookRequest.getTitle());
        return book.getId();
    }

    @PutMapping("/api/books/{id}")
    public Long updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        Book book = bookService.updateBook(id, bookRequest.getIsbn(), bookRequest.getTitle());
        return book.getId();
    }

    @Data
    static class BookRequest {
        private String isbn;
        private String title;
    }
}
