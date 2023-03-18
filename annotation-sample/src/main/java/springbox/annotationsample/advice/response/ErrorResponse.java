package springbox.annotationsample.advice.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String msg;

    @Builder
    public ErrorResponse(String msg) {
        this.msg = msg;
    }
}
