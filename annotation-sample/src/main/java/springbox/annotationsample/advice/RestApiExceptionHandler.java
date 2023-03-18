package springbox.annotationsample.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springbox.annotationsample.advice.response.ErrorResponse;
import springbox.annotationsample.exception.NoSuchAuthorityException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(NoSuchAuthorityException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchAuthorityException(NoSuchAuthorityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder().msg(e.getMessage()).build());
    }
}
