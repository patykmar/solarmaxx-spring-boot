package cz.patyk.solarmaxx.backend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateAndTimeUtils {
    public static LocalTime toLocalTime(String time) {
        return LocalTime.parse(time);
    }
}
