package cz.patyk.solarmaxx.backend.model;

import cz.patyk.solarmaxx.backend.dto.WeekDay;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class WeekDayModel {
    private static final Byte POSITION_DIFFERENCE = 10;

    public List<WeekDay> getWeekDayWitchShortName() {
        return this.provideWeekDays().stream()
                .limit(Calendar.DAY_OF_WEEK)
                .toList();
    }

    public List<WeekDay> getWeekDayWithFullName() {
        return this.provideWeekDays().stream()
                .skip(Calendar.DAY_OF_WEEK)
                .map(this::changePositionForFullNameWeekDay)
                .toList();
    }

    private WeekDay changePositionForFullNameWeekDay(WeekDay weekDay) {
        return new WeekDay((byte) (weekDay.getPosition() - POSITION_DIFFERENCE), weekDay.getName());
    }

    public enum WEEK_DAY {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY;

        public static WEEK_DAY fromInt(int position) {
            return switch (position) {
                case 1 -> WEEK_DAY.SUNDAY;
                case 2 -> WEEK_DAY.MONDAY;
                case 3 -> WEEK_DAY.TUESDAY;
                case 4 -> WEEK_DAY.WEDNESDAY;
                case 5 -> WEEK_DAY.THURSDAY;
                case 6 -> WEEK_DAY.FRIDAY;
                case 7 -> WEEK_DAY.SATURDAY;
                default -> throw new IllegalStateException("Unexpected value: " + position);
            };
        }
    }

    private List<WeekDay> provideWeekDays() {
        List<WeekDay> weekDaysList = new ArrayList<>();
        weekDaysList.add(new WeekDay((byte) 1, "Sun"));
        weekDaysList.add(new WeekDay((byte) 2, "Mon"));
        weekDaysList.add(new WeekDay((byte) 3, "Tue"));
        weekDaysList.add(new WeekDay((byte) 4, "Wed"));
        weekDaysList.add(new WeekDay((byte) 5, "Thu"));
        weekDaysList.add(new WeekDay((byte) 6, "Fri"));
        weekDaysList.add(new WeekDay((byte) 7, "Sat"));

        weekDaysList.add(new WeekDay((byte) 11, "Sunday"));
        weekDaysList.add(new WeekDay((byte) 12, "Monday"));
        weekDaysList.add(new WeekDay((byte) 13, "Tuesday"));
        weekDaysList.add(new WeekDay((byte) 14, "Wednesday"));
        weekDaysList.add(new WeekDay((byte) 15, "Thursday"));
        weekDaysList.add(new WeekDay((byte) 16, "Friday"));
        weekDaysList.add(new WeekDay((byte) 17, "Saturday"));
        return weekDaysList;
    }

}
