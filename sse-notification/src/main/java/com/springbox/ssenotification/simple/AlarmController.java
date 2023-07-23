package com.springbox.ssenotification.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class AlarmController {

    private final Map<String, SseEmitter> sseEmitterRepository = new ConcurrentHashMap<>();
    private final Map<String, HoppyEvent> eventRepository = new ConcurrentHashMap<>();

    private static final long DEFAULT_TIMEOUT = 60 * 1000 * 10L; // 10분

    @GetMapping(value = "/alarm/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @PathVariable Long id,
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId
    ) {
        // (1)
        final long userId = id;

        // (2)
        final SseEmitter emitter = getEmitter(userId);

        // (3)
        String key = createKey(userId);
        HoppyEvent event = createEvent(key, "dummy", "최초 연결 성공");
        // (4)
        sendToClient(userId, key, event);

        // (5)
        if(isNotEmpty(lastEventId)) {
            checkMissingData(userId, lastEventId);
        }

        // 디버깅용
        printEmitterList();

        return emitter;
    }

    private boolean isNotEmpty(String lastEventId) {
        System.out.println("lastEventId = " + lastEventId);
        return StringUtils.hasText(lastEventId);
    }

    private void checkMissingData(long userId, String lastEventId) {
        eventRepository.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId + "-"))
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendToClient(userId, entry.getKey(), entry.getValue()));
    }

    private SseEmitter getEmitter(long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        String key = createKey(userId);
        sseEmitterRepository.put(key, emitter);

        /**
         * SseEmmiter 에 Timeout, Completion, Error 이벤트 발생시 처리할 콜백 함수
         */
        emitter.onCompletion(() ->
                sseEmitterRepository.remove(key)
        );
        emitter.onTimeout(() -> {
            emitter.complete();
            sseEmitterRepository.remove(key);
        });
        emitter.onError((e) -> {
            emitter.completeWithError(e);
            sseEmitterRepository.remove(key);
        });

        return emitter;
    }

    // (6)
    @GetMapping("/test")
    public void testSendData() {
        printEmitterList();

        final long userId = 1L;

        String eventId = createKey(userId);

        HoppyEvent event = createEvent(eventId, "alarm", UUID.randomUUID().toString().substring(0, 8));

        sendToClient(userId, eventId, event);
    }

    private void sendToClient(long userId, String eventId, HoppyEvent event) {

        ObjectMapper mapper = new ObjectMapper();

        log.info("sendToClient");
        sseEmitterRepository.entrySet().removeIf(E -> {
            try {
                if(E.getKey().startsWith(userId + "-")) {
                    E.getValue().send(
                            SseEmitter.event()
                                    .id(eventId)
                                    .data(mapper.writeValueAsString(event), MediaType.TEXT_EVENT_STREAM)
                    );
                }
                return false;
            } catch (Exception e) {
                E.getValue().completeWithError(e);
                return true;
            }
        });
    }

    private HoppyEvent createEvent(String key, String type, String url) {
        HoppyEvent event = new HoppyEvent(type, url);
        eventRepository.put(key, event);
        return event;
    }

    private String createKey(long userId) {
        StringBuilder builder = new StringBuilder();
        return builder.append(userId)
                .append('-')
                .append(System.currentTimeMillis())
                .toString();
    }

    private void printEmitterList() {
        log.info("emitter List");
        sseEmitterRepository.forEach((key, emitter) -> {
            log.info("{}: {}", key, emitter);
        });
    }

}
