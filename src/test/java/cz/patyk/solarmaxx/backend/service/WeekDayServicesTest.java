package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.WeekDay;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class WeekDayServicesTest {

    WeekDayServices weekDayServices;

    @BeforeEach
    void setUp() {
        weekDayServices = new WeekDayServices(new WeekDayModel());
    }

    @ParameterizedTest
    @MethodSource("providePositionAndShortNameOfWeekDay")
    void getWeekDayWitchShortName(Integer index, Byte expectedPosition, String expectedName) {
        Assertions.assertThat(weekDayServices.getWeekDayWitchShortName())
                .isNotNull()
                .hasSize(7)
                .element(index)
                .returns(expectedName, WeekDay::getName)
                .returns(expectedPosition, WeekDay::getPosition);
    }

    private static Stream<Arguments> providePositionAndShortNameOfWeekDay() {
        return Stream.of(
                Arguments.of(0, (byte) 1, "Sun", WeekDayModel.WEEK_DAY.SUNDAY),
                Arguments.of(1, (byte) 2, "Mon", WeekDayModel.WEEK_DAY.MONDAY),
                Arguments.of(2, (byte) 3, "Tue", WeekDayModel.WEEK_DAY.TUESDAY),
                Arguments.of(3, (byte) 4, "Wed", WeekDayModel.WEEK_DAY.WEDNESDAY),
                Arguments.of(4, (byte) 5, "Thu", WeekDayModel.WEEK_DAY.THURSDAY),
                Arguments.of(5, (byte) 6, "Fri", WeekDayModel.WEEK_DAY.FRIDAY),
                Arguments.of(6, (byte) 7, "Sat", WeekDayModel.WEEK_DAY.SATURDAY)
        );
    }

    @ParameterizedTest
    @MethodSource("providePositionAndFullNameOfWeekDay")
    void getWeekDayWithFullName(Integer index, Byte expectedPosition, String expectedName) {
        Assertions.assertThat(weekDayServices.getWeekDayWithFullName())
                .isNotNull()
                .hasSize(7)
                .element(index)
                .returns(expectedName, WeekDay::getName)
                .returns(expectedPosition, WeekDay::getPosition);
    }

    private static Stream<Arguments> providePositionAndFullNameOfWeekDay() {
        return Stream.of(
                Arguments.of(0, (byte) 1, "Sunday"),
                Arguments.of(1, (byte) 2, "Monday"),
                Arguments.of(2, (byte) 3, "Tuesday"),
                Arguments.of(3, (byte) 4, "Wednesday"),
                Arguments.of(4, (byte) 5, "Thursday"),
                Arguments.of(5, (byte) 6, "Friday"),
                Arguments.of(6, (byte) 7, "Saturday")
        );
    }
}