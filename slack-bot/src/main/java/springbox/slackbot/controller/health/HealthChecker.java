package springbox.slackbot.controller.health;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthChecker {

    @GetMapping("/health")
    public HealthResponse check() {
        return new HealthResponse("OK");
    }

    @Getter
    static class HealthResponse {
        private String status;

        public HealthResponse(String status) {
            this.status = status;
        }
    }
}
