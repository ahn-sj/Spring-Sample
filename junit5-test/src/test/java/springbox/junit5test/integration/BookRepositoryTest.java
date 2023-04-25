package springbox.junit5test.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springbox.junit5test.book.Book;
import springbox.junit5test.book.BookRepository;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("책을 DB에 저장한다.")
    void save() throws Exception {
        // given
        final Book book = new Book("A001", "도메인 주도 개발 시작하기");

        // when
        Book saveBook = bookRepository.save(book);

        // then
        assertThat(saveBook).extracting("ISBN", "title").contains("A001", "도메인 주도 개발 시작하기");
    }

}