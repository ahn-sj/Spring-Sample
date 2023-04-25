package springbox.junit5test.book.dto;

import lombok.Data;

@Data
public class BookRequest {
    private String title;

    public BookRequest(String title) {
        this.title = title;
    }
}
