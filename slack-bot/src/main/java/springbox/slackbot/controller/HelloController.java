package springbox.slackbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springbox.slackbot.dto.SlackCommandRequest;

@RestController
@Slf4j
public class HelloController {

    @PostMapping("/hello")
    public String hello(SlackCommandRequest slackCommandRequest) {
        log.info("request = {}", slackCommandRequest);

        return "안녕하세요. 'MOMO 봇' 입니다.";
    }
}
