package com.springbox.ssenotification.complex;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResponse {

    private Long id;
    private String content;
    private String url;
    private boolean isRead;
    private Long memberId;

    public NotificationResponse(Long id, String content, String url, boolean isRead, Long memberId) {
        this.id = id;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
        this.memberId = memberId;
    }

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getContent(),
                notification.getUrl(),
                notification.isRead(),
                notification.getMemberId());
    }
}
