package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.WeekDay;

import java.util.List;

public interface IWeekDay {

    List<WeekDay> getWeekDayWitchShortName();

    List<WeekDay> getWeekDayWithFullName();
}
