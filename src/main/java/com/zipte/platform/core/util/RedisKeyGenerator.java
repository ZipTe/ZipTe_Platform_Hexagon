package com.zipte.platform.core.util;


import static com.zipte.platform.core.util.CacheNames.*;

public class RedisKeyGenerator {

    public static String getNotificationReadTime(Long userId) {

        return NOTIFICATION + SEPARATOR + userId;
    }

    public static String getTempUser(String state) {

        return OAUTH2 + SEPARATOR + TEMP + SEPARATOR + state;
    }


}
