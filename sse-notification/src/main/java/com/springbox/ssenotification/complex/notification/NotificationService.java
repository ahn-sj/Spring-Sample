package com.springbox.ssenotification.complex.notification;

import com.springbox.ssenotification.complex.member.Member;
import com.springbox.ssenotification.complex.review.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    private static final Long DEFAULT_TIMEOUT = 15L * 60L * 1000L; // 15분

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void send(Member receiver, Review review, String content) {
        Notification notification = createNotification(receiver, review, content);
        notificationRepository.save(notification);

        String id = String.valueOf(receiver.getId());

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(id);
        for (Map.Entry<String, SseEmitter> entry : sseEmitters.entrySet()) {
            String key = entry.getKey();
            SseEmitter emitter = entry.getValue();
            // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
            emitterRepository.saveEventCache(key, notification);
            // 데이터 전송
            sendNotification(emitter, key, NotificationResponse.from(notification));
        }
        printEmitterList();
    }

    private Notification createNotification(Member receiver, Review review, String content) {
        return new Notification(content, "/reviews/" + review.getId(), false, receiver.getId(), NotificationType.REVIEW);
    }

    public SseEmitter subscribe(Long memberId, String lastEventId) {
        String emitterId = makeCurrentTimeWithId(memberId);

        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        // key: ${user_id}_${System.currentTimeMillis()}
        // value: SseEmitter

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId)); // SseEmitter의 에러 발생
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));    // SseEmitter의 타임 아웃 발생

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendNotification(emitter, emitterId, "EventStream Created. [userId=" + memberId + "]");

        // Last-Event-ID값이 헤더에 있는 경우 (미수신한 Event가 존재) 유실된 데이터 재전송
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberId, emitterId, emitter);
        }
        return emitter;
    }

    private String makeCurrentTimeWithId(Long memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, Object data) {
        System.out.println("emitter = " + emitter);
        System.out.println("eventId = " + eventId);
        System.out.println("data = " + data);
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(eventId);
            throw new RuntimeException("연결 오류");
        }
    }

    private boolean hasLostData(String lastEventId) {
        return StringUtils.hasText(lastEventId);
    }

    private void sendLostData(String lastEventId, Long memberId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithById(String.valueOf(memberId));
        for (Map.Entry<String, Object> entry : eventCaches.entrySet()) {
            if (lastEventId.compareTo(entry.getKey()) < 0) {
                sendNotification(emitter, entry.getKey(), emitterId);
            }
        }
    }

    private void printEmitterList() {
        log.info("emitter List");
        emitterRepository.print();
    }
}
