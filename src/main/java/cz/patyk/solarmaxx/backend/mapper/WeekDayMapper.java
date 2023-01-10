package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.backend.dto.WeekDay;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeekDayMapper {

    private final WeekDayModel weekDayModel;

    public WeekDay toWeekDayShortName(WeekDayModel.WEEK_DAY weekDay) {
        int position = toIntPositionFromWeekDayEnum(weekDay);
        return weekDayModel.getWeekDayWitchShortName().get(position - 1);
    }

    public WeekDay toWeekDayFullName(WeekDayModel.WEEK_DAY weekDay) {
        int position = toIntPositionFromWeekDayEnum(weekDay);
        return weekDayModel.getWeekDayWithFullName().get(position - 1);
    }

    public WeekDayModel.WEEK_DAY toEnumFromWeekDay(WeekDay weekDay) {
        return WeekDayModel.WEEK_DAY.fromInt(weekDay.getPosition());
    }

    public WeekDayModel.WEEK_DAY fromPositionToWeekDayEnum(int position) {
        return WeekDayModel.WEEK_DAY.fromInt(position);
    }

    public Byte toBytePositionFromWeekDayEnum(WeekDayModel.WEEK_DAY weekDay) {
        return switch (weekDay) {
            case SUNDAY -> (byte) 1;
            case MONDAY -> (byte) 2;
            case TUESDAY -> (byte) 3;
            case WEDNESDAY -> (byte) 4;
            case THURSDAY -> (byte) 5;
            case FRIDAY -> (byte) 6;
            case SATURDAY -> (byte) 7;
        };
    }

    public Integer toIntPositionFromWeekDayEnum(WeekDayModel.WEEK_DAY weekDay) {
        return Integer.valueOf(toBytePositionFromWeekDayEnum(weekDay));
    }
}
