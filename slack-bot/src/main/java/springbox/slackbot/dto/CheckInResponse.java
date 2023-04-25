package springbox.slackbot.dto;

import lombok.Data;
import springbox.slackbot.utils.Formatter;

import java.time.LocalDateTime;

@Data
public class CheckInResponse {
    private String checkInTime;
    private String username;

    public CheckInResponse(LocalDateTime checkInTime, String username) {
        this.checkInTime = Formatter.format(checkInTime);
        this.username = username;
    }
}
