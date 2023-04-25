package springbox.junit5test.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void registerBook(String title) {
        Book book = bookRepository.save(new Book(UUID.randomUUID().toString(), title));

        log.info("{} 의 책을 등록하였습니다.", title);
    }

    public List<Book> findBooks() {
        log.info("call BookService#findBooks");
        return bookRepository.findAll();

    }
}
