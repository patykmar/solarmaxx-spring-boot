package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.WeekDay;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WeekDayServices implements IWeekDay {
    private final WeekDayModel weekDayModel;

    public List<WeekDay> getWeekDayWitchShortName() {
        return weekDayModel.getWeekDayWitchShortName();
    }

    public List<WeekDay> getWeekDayWithFullName() {
        return weekDayModel.getWeekDayWithFullName();
    }

}
