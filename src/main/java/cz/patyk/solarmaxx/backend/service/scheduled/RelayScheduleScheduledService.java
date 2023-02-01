package cz.patyk.solarmaxx.backend.service.scheduled;

import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayMapper;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import cz.patyk.solarmaxx.backend.service.RelayScheduleService;
import cz.patyk.solarmaxx.backend.service.RelayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelayScheduleScheduledService {
    private final RelayScheduleService relayScheduleService;
    private final RelayService relayService;
    private final RelayMapper relayMapper;
    private final RelayOutputService relayOutputService;
    private final Calendar calendar = Calendar.getInstance();
    private final Byte weekDay = (byte) calendar.get(Calendar.DAY_OF_WEEK);

    @Scheduled(fixedDelay = 30000)
    void setRelayOutputToOn() {
        log.info("Starting method setRelayOutputToOn");
        // select all RelayOutput which should have state ON
        // check affected relay output their status
        List<RelayDtoOut> relayDtoOuts = extractRelayEntityFromRelaySchedule(
                relayScheduleService.getAllRelayScheduleByDayNumberWhichShouldBeEnable(weekDay)
        );

        log.info("Number of outputs which should be turn ON is: {}", relayDtoOuts.size());

        // set state On for those which are in Off state
        filterOutRelayOutputDtoByOutputStatus(relayDtoOuts, OutputStatus.ON)
                .forEach(this::turnOnRelayOutput);

        log.info("Finished method setRelayOutputToOn");
    }

    @Scheduled(fixedDelay = 30000)
    void setRelayOutputToOff() {
        log.info("Starting method setRelayOutputToOff");
        // select all RelayOutput which should have state Off
        // check affected relay output their status
        List<RelayDtoOut> relayDtoOuts = extractRelayEntityFromRelaySchedule(
                relayScheduleService.getAllRelayScheduleByDayNumberWhichShouldBeDisable(weekDay)
        );

        log.info("Number of outputs which should be turn OFF is: {}", relayDtoOuts.size());

        // filtered out ports which are Off
        // set state Off for those which are in On state
        filterOutRelayOutputDtoByOutputStatus(relayDtoOuts, OutputStatus.OFF)
                .forEach(this::turnOffRelayOutput);

        log.info("Finished method setRelayOutputToOff");
    }

    private void turnOnRelayOutput(RelayOutputDto relayOutputDto) {
        relayOutputService.toggleOutput(
                relayOutputDto.getRelayId(),
                relayOutputDto.getOutputId(),
                true);
    }

    private void turnOffRelayOutput(RelayOutputDto relayOutputDto) {
        relayOutputService.toggleOutput(
                relayOutputDto.getRelayId(),
                relayOutputDto.getOutputId(),
                false);
    }

    public List<RelayOutputDto> filterOutRelayOutputDtoByOutputStatus(List<RelayDtoOut> relayDtoOuts, OutputStatus outputStatus) {
        return relayDtoOuts.stream()
                .map(RelayDtoOut::getRelayOutputDtos)
                .flatMap(Collection::stream)
                .filter(relayOutputDto -> relayOutputDto.getOutputStatus() != outputStatus)
                .toList();
    }

    public List<RelayDtoOut> extractRelayEntityFromRelaySchedule(List<RelayScheduleDtoOut> relayScheduleDtoOuts) {
        return relayScheduleDtoOuts.stream()
                .map(relayScheduleDtoOut -> relayService.getOneEntity(relayScheduleDtoOut.getRelayId()))
                .map(relayMapper::toDtoOutOnLineMode)
                .toList();
    }
}
