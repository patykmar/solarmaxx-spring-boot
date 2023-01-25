package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.ValueConstants;
import cz.patyk.solarmaxx.backend.dto.WeekDay;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class WeekDayMapperTest {

    WeekDayMapper weekDayMapper;

    @BeforeEach
    void setUp() {
        weekDayMapper = new WeekDayMapper(new WeekDayModel());
    }

    @ParameterizedTest
    @MethodSource("provideEnumWeekDaysAndShortName")
    void toWeekDayShortName(Byte expectedPosition, String expectedName, WeekDayModel.WEEK_DAY enteredDay) {
        Assertions.assertThat(weekDayMapper.toWeekDayShortName(enteredDay))
                .returns(expectedPosition, WeekDay::getPosition)
                .returns(expectedName, WeekDay::getName);
    }

    private static Stream<Arguments> provideEnumWeekDaysAndShortName() {
        return Stream.of(
                Arguments.of((byte) 1, ValueConstants.WEEK_DAY_SUN, WeekDayModel.WEEK_DAY.SUNDAY),
                Arguments.of((byte) 2, ValueConstants.WEEK_DAY_MON, WeekDayModel.WEEK_DAY.MONDAY),
                Arguments.of((byte) 3, ValueConstants.WEEK_DAY_TUE, WeekDayModel.WEEK_DAY.TUESDAY),
                Arguments.of((byte) 4, ValueConstants.WEEK_DAY_WED, WeekDayModel.WEEK_DAY.WEDNESDAY),
                Arguments.of((byte) 5, ValueConstants.WEEK_DAY_THU, WeekDayModel.WEEK_DAY.THURSDAY),
                Arguments.of((byte) 6, ValueConstants.WEEK_DAY_FRI, WeekDayModel.WEEK_DAY.FRIDAY),
                Arguments.of((byte) 7, ValueConstants.WEEK_DAY_SAT, WeekDayModel.WEEK_DAY.SATURDAY)
        );
    }

    @ParameterizedTest
    @MethodSource("provideEnumWeekDaysAndFullName")
    void toWeekDayFullName(Byte expectedPosition, String expectedName, WeekDayModel.WEEK_DAY enteredDay) {
        Assertions.assertThat(weekDayMapper.toWeekDayFullName(enteredDay))
                .returns(expectedPosition, WeekDay::getPosition)
                .returns(expectedName, WeekDay::getName);
    }

    private static Stream<Arguments> provideEnumWeekDaysAndFullName() {
        return Stream.of(
                Arguments.of((byte) 1, ValueConstants.WEEK_DAY_SUNDAY, WeekDayModel.WEEK_DAY.SUNDAY),
                Arguments.of((byte) 2, ValueConstants.WEEK_DAY_MONDAY, WeekDayModel.WEEK_DAY.MONDAY),
                Arguments.of((byte) 3, ValueConstants.WEEK_DAY_TUESDAY, WeekDayModel.WEEK_DAY.TUESDAY),
                Arguments.of((byte) 4, ValueConstants.WEEK_DAY_WEDNESDAY, WeekDayModel.WEEK_DAY.WEDNESDAY),
                Arguments.of((byte) 5, ValueConstants.WEEK_DAY_THURSDAY, WeekDayModel.WEEK_DAY.THURSDAY),
                Arguments.of((byte) 6, ValueConstants.WEEK_DAY_FRIDAY, WeekDayModel.WEEK_DAY.FRIDAY),
                Arguments.of((byte) 7, ValueConstants.WEEK_DAY_SATURDAY, WeekDayModel.WEEK_DAY.SATURDAY)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWeekDayAndExpectedEnum")
    void toEnumWeekDay(WeekDay providedWeekDay, WeekDayModel.WEEK_DAY expectedWeekDay) {
        Assertions.assertThat(weekDayMapper.toEnumFromWeekDay(providedWeekDay))
                .isEqualTo(expectedWeekDay);
    }

    @Test
    void toEnumFromWeekDayWrongParameter() {
        Assertions.assertThatThrownBy(() -> weekDayMapper.toEnumFromWeekDay(new WeekDay((byte) 8, "Some unexpected name of day")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Unexpected value: 8");
    }

    private static Stream<Arguments> provideWeekDayAndExpectedEnum() {
        return Stream.of(
                Arguments.of(new WeekDay((byte) 1, ValueConstants.WEEK_DAY_SUNDAY), WeekDayModel.WEEK_DAY.SUNDAY),
                Arguments.of(new WeekDay((byte) 2, ValueConstants.WEEK_DAY_MONDAY), WeekDayModel.WEEK_DAY.MONDAY),
                Arguments.of(new WeekDay((byte) 3, ValueConstants.WEEK_DAY_TUESDAY), WeekDayModel.WEEK_DAY.TUESDAY),
                Arguments.of(new WeekDay((byte) 4, ValueConstants.WEEK_DAY_WEDNESDAY), WeekDayModel.WEEK_DAY.WEDNESDAY),
                Arguments.of(new WeekDay((byte) 5, ValueConstants.WEEK_DAY_THURSDAY), WeekDayModel.WEEK_DAY.THURSDAY),
                Arguments.of(new WeekDay((byte) 6, ValueConstants.WEEK_DAY_FRIDAY), WeekDayModel.WEEK_DAY.FRIDAY),
                Arguments.of(new WeekDay((byte) 7, ValueConstants.WEEK_DAY_SATURDAY), WeekDayModel.WEEK_DAY.SATURDAY)
        );
    }

    @ParameterizedTest
    @MethodSource("providePositionAndExpectedEnum")
    void fromPositionToWeekDayEnum(Byte providedPosition, WeekDayModel.WEEK_DAY expectedWeekDay) {
        Assertions.assertThat(weekDayMapper.fromPositionToWeekDayEnum(providedPosition))
                .isEqualTo(expectedWeekDay);
    }

    @Test
    void fromPositionToWeekDayEnumWronParameter() {
        Assertions.assertThatThrownBy(() -> weekDayMapper.fromPositionToWeekDayEnum(8))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Unexpected value: 8");
    }

    private static Stream<Arguments> providePositionAndExpectedEnum() {
        return Stream.of(
                Arguments.of((byte) 1, WeekDayModel.WEEK_DAY.SUNDAY),
                Arguments.of((byte) 2, WeekDayModel.WEEK_DAY.MONDAY),
                Arguments.of((byte) 3, WeekDayModel.WEEK_DAY.TUESDAY),
                Arguments.of((byte) 4, WeekDayModel.WEEK_DAY.WEDNESDAY),
                Arguments.of((byte) 5, WeekDayModel.WEEK_DAY.THURSDAY),
                Arguments.of((byte) 6, WeekDayModel.WEEK_DAY.FRIDAY),
                Arguments.of((byte) 7, WeekDayModel.WEEK_DAY.SATURDAY)
        );
    }

    @ParameterizedTest
    @MethodSource("providePositionAndExpectedEnum")
    void toBytePositionFromWeekDayEnum(Byte expectedPosition, WeekDayModel.WEEK_DAY providedWeekDay) {
        Assertions.assertThat(weekDayMapper.toBytePositionFromWeekDayEnum(providedWeekDay))
                .isInstanceOf(Byte.class)
                .isEqualTo(expectedPosition);
    }

    @ParameterizedTest
    @MethodSource("providePositionAndExpectedEnum")
    void toIntPositionFromWeekDayEnum(Byte expectedPosition, WeekDayModel.WEEK_DAY providedWeekDay) {
        Assertions.assertThat(weekDayMapper.toIntPositionFromWeekDayEnum(providedWeekDay))
                .isInstanceOf(Integer.class)
                .isEqualTo(Integer.valueOf(expectedPosition));
    }
}