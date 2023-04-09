package springbox.slackbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @PostMapping("/hello")
    public String hello(SlackCommandRequest slackCommandRequest) {
        log.info("request = {}", slackCommandRequest);
        return "hello";
    }
}
