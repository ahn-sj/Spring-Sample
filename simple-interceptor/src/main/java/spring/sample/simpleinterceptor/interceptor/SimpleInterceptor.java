package spring.sample.simpleinterceptor.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import spring.sample.simpleinterceptor.computer.repository.ComputerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
public class SimpleInterceptor implements HandlerInterceptor {

    private final ComputerRepository computerRepository;

    public SimpleInterceptor(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("========== START ==========");
        String requestURI = request.getRequestURI();
        log.info(requestURI);

        if (requestURI.contains("order")) {
            Long computerId = Long.valueOf(requestURI.split("/")[3]);

            LocalDateTime findStartedDate = computerRepository.findById(computerId).orElseThrow().getStartedDate();
            LocalDateTime nowDate = LocalDateTime.of(2022, 12, 05, 16, 30);
            log.info("findStartedDate = " + findStartedDate);
            log.info("nowDate = " + nowDate);

            if(nowDate.compareTo(findStartedDate) != 1) {
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println(
                        "<script>" +
                                "alert('이벤트 시간이 아닙니다.');" +
                                "history.go(-1);" +
                                "</script>"
                );
                response.getWriter().flush();

                log.info("XXXX Event time is FALSE XXXX");
                return false;
            } else {
                log.info("OOOO Event time is TRUE OOOO");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("========== END ==========");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
