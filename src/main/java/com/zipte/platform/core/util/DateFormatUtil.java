package com.zipte.platform.core.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateFormatUtil {

    public static String formatPostDate(LocalDateTime created) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(created, now);

        if (duration.toMinutes() < 60) {
            return duration.toMinutes() + "분 전";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + "시간 전";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREA);
            return created.format(formatter);
        }
    }
}
