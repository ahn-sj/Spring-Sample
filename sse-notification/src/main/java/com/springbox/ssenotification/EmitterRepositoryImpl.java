package com.springbox.ssenotification;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId) {
        Map<String, SseEmitter> map = new HashMap<>();

        for (Map.Entry<String, SseEmitter> entry : emitters.entrySet()) {
            if (entry.getKey().startsWith(memberId)) {
                if (map.put(entry.getKey(), entry.getValue()) != null) {
                    throw new IllegalStateException("Duplicate key");
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId) {
        Map<String, Object> map = new HashMap<>();

        for (Map.Entry<String, Object> entry : eventCache.entrySet()) {
            if (entry.getKey().startsWith(memberId)) {
                if (map.put(entry.getKey(), entry.getValue()) != null) {
                    throw new IllegalStateException("Duplicate key");
                }
            }
        }
        return map;
    }

    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllEmitterStartWithId(String memberId) {
        for (Map.Entry<String, SseEmitter> entry : emitters.entrySet()) {
            String key = entry.getKey();
            SseEmitter emitter = entry.getValue();

            if (key.startsWith(memberId)) {
                emitters.remove(key);
            }
        }
    }

    @Override
    public void deleteAllEventCacheStartWithId(String memberId) {
        for (Map.Entry<String, Object> entry : eventCache.entrySet()) {
            String key = entry.getKey();
            Object emitter = entry.getValue();

            if (key.startsWith(memberId)) {
                eventCache.remove(key);
            }
        }

    }
}
