package spring.sample.simpleinterceptor.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper objectMapper = new ObjectMapper();

    public SimpleInterceptor(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("========== START ==========");
        String requestURI = request.getRequestURI();
        log.info(requestURI);

        if (!requestURI.contains("order")) {
            return true;
        }

        Long computerId = Long.valueOf(requestURI.split("/")[3]);

        LocalDateTime start = computerRepository.findById(computerId).orElseThrow().getStartedDate();
        LocalDateTime now = LocalDateTime.of(2022, 12, 05, 16, 30);
        log.info("start = " + start);
        log.info("now = " + now);

        if (now.compareTo(start) != 1) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(400);

            MessageDto messageDto = new MessageDto("이벤트 시작일이 아닙니다.");
            String responseDto = objectMapper.writeValueAsString(messageDto);
            response.getWriter().write(responseDto);

            log.info("XXXX Event time is FALSE XXXX");

            return false;
        }
        log.info("OOOO Event time is TRUE OOOO");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("========== END ==========");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    static class MessageDto {
        private String msg;

        public MessageDto(String msg) {
            this.msg = msg;
        }
    }
}
