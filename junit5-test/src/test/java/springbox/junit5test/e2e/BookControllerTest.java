package springbox.junit5test.e2e;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springbox.junit5test.book.BookController;
import springbox.junit5test.book.BookService;
import springbox.junit5test.book.dto.BookRequest;


@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        System.out.println("mockMvc = " + mockMvc);
        System.out.println("bookService = " + bookService);
        System.out.println("bookController = " + bookController);
    }

    @Test
    void saveBookTest() throws Exception {
        BookRequest bookRequest = createBookRequest();

        // given

        // when

        // then
    }

    private BookRequest createBookRequest() {
        return new BookRequest("알고리즘 코딩 테스트");
    }


}