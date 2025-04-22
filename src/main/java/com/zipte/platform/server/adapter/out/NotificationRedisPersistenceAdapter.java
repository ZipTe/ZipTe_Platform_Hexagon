package com.zipte.platform.server.adapter.out;

import com.zipte.platform.core.util.RedisKeyGenerator;
import com.zipte.platform.server.application.out.notification.TimeNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class NotificationRedisPersistenceAdapter implements TimeNotificationPort {

    /*
        90일동안, 유저의 읽은 시간을 기록하는 구조입니다.
     */

    private final RedisTemplate<String, Long> redisTemplate;

    @Override
    public void setLatestReadAt(Long userId) {
        String key = RedisKeyGenerator.getNotificationReadTime(userId);
        redisTemplate.opsForValue().set(key, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        // TTL 30일 설정
        redisTemplate.expire(key, 7, TimeUnit.DAYS);
    }

    @Override
    public LocalDateTime getLatestReadAt(Long userId) {
        String key = RedisKeyGenerator.getNotificationReadTime(userId);
        Long lastReadAt = redisTemplate.opsForValue().get(key);

        if (lastReadAt == null) {
            return null;
        }

        return LocalDateTime.ofEpochSecond(lastReadAt, 0, ZoneOffset.UTC);

    }
}
