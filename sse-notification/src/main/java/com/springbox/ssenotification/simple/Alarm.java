package com.springbox.ssenotification.simple;

import com.springbox.ssenotification.complex.NotificationType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id @GeneratedValue
    @Column(name = "alarm_id")
    private Long id;

    private String content;

    private String url;

    private boolean isRead;

    private Long memberId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public Alarm(String content, String url, boolean isRead, Long memberId, NotificationType notificationType) {
        this.content = content;
        this.url = url;
        this.isRead = isRead;
        this.memberId = memberId;
        this.notificationType = notificationType;
    }


}
