package springbox.slackbot.dto;

import lombok.Data;
import springbox.slackbot.utils.Formatter;

import java.time.LocalDateTime;

@Data
public class CheckOutResponse {
    private String checkOutTime;
    private String workTime;
    private String username;

    public CheckOutResponse(LocalDateTime checkOutTime, String workTime, String username) {
        this.checkOutTime = Formatter.format(checkOutTime);
        this.workTime = workTime;
        this.username = username;
    }
}
