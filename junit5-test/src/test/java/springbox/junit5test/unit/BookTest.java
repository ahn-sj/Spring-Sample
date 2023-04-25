package springbox.junit5test.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springbox.junit5test.book.Book;

import java.util.UUID;

class BookTest {

    @Test
    @DisplayName("책의 이름을 수정한다.")
    void updateBook() throws Exception {
        // given
        Book book = new Book(UUID.randomUUID().toString(), "자바 ORM 표준 JPA 프로그래밍");

        // when
        book.updateTitle("자바 ORM 표준 JPA 프로그래밍2");

        // then
        Assertions.assertThat(book.getTitle()).isEqualTo("자바 ORM 표준 JPA 프로그래밍2");
    }

}