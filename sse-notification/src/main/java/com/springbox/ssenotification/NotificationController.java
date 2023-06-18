package com.springbox.ssenotification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe( // @AuthenticationPrincipal MemberDetails memberDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        SseEmitter sseEmitter = notificationService.subscribe(1L, lastEventId);
        return ResponseEntity.ok(sseEmitter).getBody();
    }
}
