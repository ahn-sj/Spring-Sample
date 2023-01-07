## ResponseEntity의 Builder로 응답을 반환해보자.

지난 프로젝트를 돌이켜보면 응답을 보내는 경우는 크게 2가지 였다.

1. ResponseEntity의 Constructor 로 반환
2. 공통으로 사용할 ResponseDto를 만들어서 반환

1번은 다음과 같이 사용했다.

```java
@ExceptionHandler(CustomCommonException.class)
public ResponseEntity<ResponseDto<Object>> customCommonException(CustomCommonException e) {
    return new ResponseEntity<>(ResponseDto.fail(e.getStatus(), e.getHttpStatus(), e.getMessage()), e.getHttpStatus());
}
```

프로젝트 전체에 예외 처리를 Custom Exception 클래스로 처리를 해주고, 전달할 값들을 입력받고, Enum으로 관리를 했기 때문에 잘못 입력할 문제는 없었다.

그러나, 향후 Constructor vs Builder와 비교를 했을 때에는 Builder의 장점으로 인해 사용할 일이 더 높다고 판단되어 적용해보고자 Builder로 응답을 해보고 싶었다.

<br/>

2번은 공통으로 사용할 ResponseDto를 만들어서 반환하는 방식이었다.

사실 이건 `Bad Code`라고 생각되어 초반에만 사용을 했었다. 그 이유는, 상태 코드를 지정해줄 수 없다는 것이다.

공통 ResponseDto는 다음과 같았다.
```java
@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    public static <T> ResponseDto<T> success(T data) {

        return new ResponseDto<>(true, data, null);
    }
    public static <T> ResponseDto<T> fail(int status, HttpStatus httpStatus, String message){
        return new ResponseDto<>(false, null, new Error(status, httpStatus, message));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private final int status;
        private final HttpStatus httpStatus;
        private final String message;
    }
}
```

그러나 아래처럼 반환을 해주어야 하기 때문에 상태 코드 값을 변경해주지 못한다는 단점이 존재했다.
```java
public class MemberController {
    ...

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseDto<MemberAuthResponseDto> registerMember(@RequestBody @Valid MemberSignupRequestDto memberRequestDto) {
        return ResponseDto.success(memberService.signup(memberRequestDto));
    }
    ...
}
```

그래서 이번엔 ResponseEntity를 Builder로 적용해보려고 한다.

<br/>

### API
```java
@PostMapping("/api/save")
@GetMapping("/api/{id}")
@DeleteMapping("/api/{id}")
@GetMapping("/api/existence/{id}")
```