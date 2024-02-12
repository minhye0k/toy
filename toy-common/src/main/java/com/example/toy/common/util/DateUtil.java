package com.example.toy.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {
    public static Date afterOf(long second) {
        return new Date(System.currentTimeMillis() + (second * 1000));
    }

    public static Date aMinutesBeforeFrom(LocalDateTime localDateTime, long minutes) {
        return java.sql.Timestamp.valueOf(localDateTime.minusMinutes(minutes));
    }

}
