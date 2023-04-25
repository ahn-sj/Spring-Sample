package springbox.junit5test.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springbox.junit5test.book.dto.BookRequest;

import java.util.List;

@Slf4j
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books/regist")
    public void registerBook(@RequestBody BookRequest bookRequest) {
        bookService.registerBook(bookRequest.getTitle());
    }

    @GetMapping("/books")
    public List<Book> findBooks() {
        return bookService.findBooks();
    }
}
