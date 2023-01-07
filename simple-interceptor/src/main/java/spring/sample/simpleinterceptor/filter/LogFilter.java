package spring.sample.simpleinterceptor.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    // [요구 사항]
    // 홈페이지에 인증된 사용자만 접근 가능하도록 해주세요.
    // GET 요청만 OK
    private static final String[] WHITE_LIST = {"/", "/api/v1"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("====== log filter init ======");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("====== log filter doFilter ======");

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("====== log filter destroy ======");
    }


    /**
     * 화이트 리스트의 경우 인증 체크 X
     */
    private boolean isCheckPath(String requestURL) {
        return !PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}
