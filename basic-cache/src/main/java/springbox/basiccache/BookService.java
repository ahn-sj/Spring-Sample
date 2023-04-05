package springbox.basiccache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(cacheNames = "books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(cacheNames = "books", key = "#id")
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Transactional
    @CacheEvict(cacheNames = "books", allEntries = true)
    public Book saveBook(String isbn, String title) {
         return bookRepository.save(new Book(isbn, title));
    }

    @Transactional
    @CachePut(cacheNames = "books", key = "#id")
    public Book updateBook(Long id, String isbn, String title) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.update(isbn, title);

        return book;
    }
}
