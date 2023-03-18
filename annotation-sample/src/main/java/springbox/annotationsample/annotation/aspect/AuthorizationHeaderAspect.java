package springbox.annotationsample.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springbox.annotationsample.domain.enums.Authority;

@Aspect
@Component
@Slf4j
public class AuthorizationHeaderAspect {

    private final static String AUTHORIZATION = "Authorization";

    @Before("@annotation(springbox.annotationsample.annotation.AuthorizationHeader)")
    public void authorization() {
        String header = getAuthorizationHeader();
        String auth = hasAuthorization(header);
        validAuthorization(auth);

        log.info("auth = {}", auth);
    }

    private static String hasAuthorization(String header) {
        validAuthorization(header);
        String auth = "";
        for (Authority authority : Authority.values()) {
            String key = String.valueOf(authority);

            if(header.equalsIgnoreCase(key)) {
                auth = header;
            }
        }
        return auth;
    }

    private static void validAuthorization(String value) {
        if(!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("권한이 필요한 요청입니다.");
        }
    }

    private String getAuthorizationHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getHeader(AUTHORIZATION);
    }
}
