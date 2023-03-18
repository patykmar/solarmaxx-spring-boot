package cz.patyk.solarmaxx.backend.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.stream.Stream;


class DateAndTimeUtilsTest {

    @ParameterizedTest
    @MethodSource("provideValidInput")
    void toLocalTime(String time, Integer hour, Integer minute) {
        Assertions.assertThat(DateAndTimeUtils.toLocalTime(time))
                .isInstanceOf(LocalTime.class)
                .hasHour(hour)
                .hasMinute(minute);
    }

    private static Stream<Arguments> provideValidInput() {
        return Stream.of(
                Arguments.of("00:00", 0, 0),
                Arguments.of("00:01", 0, 1),
                Arguments.of("01:01", 1, 1),
                Arguments.of("02:02", 2, 2),
                Arguments.of("03:03", 3, 3),
                Arguments.of("04:04", 4, 4),
                Arguments.of("05:05", 5, 5),
                Arguments.of("06:06", 6, 6),
                Arguments.of("07:07", 7, 7),
                Arguments.of("08:08", 8, 8),
                Arguments.of("09:09", 9, 9),
                Arguments.of("10:10", 10, 10),
                Arguments.of("11:11", 11, 11),
                Arguments.of("12:12", 12, 12),
                Arguments.of("13:13", 13, 13),
                Arguments.of("14:14", 14, 14),
                Arguments.of("15:15", 15, 15),
                Arguments.of("16:16", 16, 16),
                Arguments.of("17:17", 17, 17),
                Arguments.of("18:18", 18, 18),
                Arguments.of("19:19", 19, 19),
                Arguments.of("20:20", 20, 20),
                Arguments.of("21:21", 21, 21),
                Arguments.of("22:22", 22, 22),
                Arguments.of("23:23", 23, 23),
                Arguments.of("23:59", 23, 59)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputs")
    void toLocalTimeInvalid(String time) {
        Assertions.assertThatExceptionOfType(DateTimeException.class)
                .isThrownBy(() -> {
                    DateAndTimeUtils.toLocalTime(time);
                });
    }

    private static Stream<Arguments> provideInvalidInputs() {
        return Stream.of(
                Arguments.of("0:0"),
                Arguments.of("0:1"),
                Arguments.of("1:0"),
                Arguments.of("1:"),
                Arguments.of(":"),
                Arguments.of(":0"),
                Arguments.of("12:0")
        );
    }
}