package com.zipte.platform.core.util;


import static com.zipte.platform.core.util.CacheNames.NOTIFICATION;
import static com.zipte.platform.core.util.CacheNames.SEPARATOR;

public class RedisKeyGenerator {

    public static String getNotificationReadTime(Long userId) {

        return NOTIFICATION + SEPARATOR + userId;
    }


}
