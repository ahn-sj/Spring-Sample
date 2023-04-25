package springbox.slackbot.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String username;

    public Member(String username) {
        this.username = username;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public String getWorkTime() {
        int second = (int) ChronoUnit.SECONDS.between(checkInTime, checkOutTime);

        int minute = second / 60;
        int hour = minute / 60;
        second = second % 60;

        System.out.printf("%d:%02d:%02d", hour, minute, second);

        return hour + "시간 " + minute + "분 " + second + "초";
    }

    public void checkOut() {
        checkOutTime = LocalDateTime.now();
    }
}
