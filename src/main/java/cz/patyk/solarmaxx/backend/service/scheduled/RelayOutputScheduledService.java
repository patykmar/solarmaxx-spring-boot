package cz.patyk.solarmaxx.backend.service.scheduled;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputScheduleDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import cz.patyk.solarmaxx.backend.service.RelayOutputScheduleService;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelayOutputScheduledService {
    private final RelayOutputService relayOutputService;
    private final RelayAdapterFactory relayAdapterFactory;
    private final RelayOutputScheduleService relayOutputScheduleService;

    private final Calendar calendar = Calendar.getInstance();
    private final Byte weekDay = (byte) calendar.get(Calendar.DAY_OF_WEEK);
    private final LocalTime actualTime = LocalTime.now();

    @Scheduled(fixedDelay = 30000)
    void updateOutputStateFromDevices() {
        log.info("Starting method updateOutputStateFromDevices");
        // fetch all outputs
        List<RelayOutputDataDto> relayOutputDataDtos = relayOutputService.getAll();

        List<RelayOutput> relayOutputs = getRealStateOfRelayOutputs(relayOutputDataDtos);

        // update their state into database
        relayOutputs.forEach(relayOutput -> {
            raiseLogBasedOnOutputState(relayOutput);
            relayOutputService.editItemByEntity(relayOutput);
        });

        log.info("Finished method updateOutputStateFromDevices");
    }


    // Method collected all outputs which should be up
    // for concrete day of week
    // filtered out their which are already up and
    // rest of ports try turn on
    void collectAllRelayOutputsWhichShouldBeUpBySchedulerForConcreteDayOfWeek() {
        log.info("Starting method collectAllRelayOutputsWhichShouldBeUpBySchedulerForConcreteDayOfWeek");

        // load output port based on their schedule for concrete week day
        relayOutputScheduleService.getAllByWeekDayNumber(weekDay).stream()
                .filter(relayOutputScheduleDataDto -> isActualTimeInRange(relayOutputScheduleDataDto.getOnStart(), relayOutputScheduleDataDto.getOnEnd()))
                .filter(this::isNotRelayOutputStatusOn)
                .map(relayOutputScheduleDataDto -> relayOutputService.getOne(relayOutputScheduleDataDto.getRelayOutputId()))
                .map(this::setStatusOn)
                .map(relayOutputService::toEntityFromDevice)
                .forEach(this::raiseLogBasedOnOutputState);

//        log.info("Size of List<relayOutputSchedulesDataDto> is: {}", relayOutputSchedulesDataDto.size());


        log.info("Finished method collectAllRelayOutputsWhichShouldBeUpBySchedulerForConcreteDayOfWeek");
    }

//    private List<RelayOutput> getRelayOutputByWeekDayWhichShouldBeUp() {
//        relayOutputService.get
//    }


    // method collect all port and filtered out their which
    // should be down by schedule

    /**
     * Select all relay outputs
     */
    public void setRelayOutputToOn() {

    }

    /**
     * Method connect to devices via their adapter and check status of their outputs.
     * Real state of outputs are saved to field {@code deviceOutputStatus} of {@link RelayOutputDataDto}.
     * Based on compare field {@code outputStatus} and {@code deviceOutputStatus} there are filtered out
     * these items which are the same state.
     */
    private List<RelayOutput> getRealStateOfRelayOutputs(List<RelayOutputDataDto> relayOutputDataDtos) {
        long startTime = System.nanoTime();
        List<RelayOutput> relayOutputs = relayOutputDataDtos.parallelStream()
                .map(this::checkStatusFromRelay)
                .filter(this::isDeviceStatusDifferentThanRealState)
                .map(relayOutputService::toEntityFromDevice)
                .toList();
        long endTime = System.nanoTime();
        long resultTime = endTime - startTime;
        log.info("Collect port status takes: {} seconds", (float) resultTime / 1000000000);
        return relayOutputs;
    }

    private RelayOutputDataDto checkStatusFromRelay(RelayOutputDataDto relayOutputDataDto) {
        RelayAdapter relayAdapter = relayAdapterFactory.getRelayAdapter(relayOutputDataDto.getRelayTypeEnum());
        return relayAdapter.updateStatusFromRelay(relayOutputDataDto);
    }

    private RelayOutputDataDto setStatusOn(RelayOutputDataDto relayOutputDataDto){
        RelayAdapter relayAdapter = relayAdapterFactory.getRelayAdapter(relayOutputDataDto.getRelayTypeEnum());
        return relayAdapter.turnOnRelayOutput(relayOutputDataDto);
    }

    private RelayOutputDataDto setStatusOff(RelayOutputDataDto relayOutputDataDto){
        RelayAdapter relayAdapter = relayAdapterFactory.getRelayAdapter(relayOutputDataDto.getRelayTypeEnum());
        return relayAdapter.turnOffRelayOutput(relayOutputDataDto);
    }

    private boolean isDeviceStatusDifferentThanRealState(RelayOutputDataDto relayOutputDataDto) {
        return !relayOutputDataDto.getOutputStatus().equals(relayOutputDataDto.getDeviceOutputStatus());
    }

    void raiseLogBasedOnOutputState(RelayOutput relayOutput) {
        if (OutputStatus.NA.equals(relayOutput.getOutputStatus())) {
            log.error("\nUpdate device id: {}, description: {}, state: {}\n",
                    relayOutput.getId(),
                    relayOutput.getDescription(),
                    relayOutput.getOutputStatus().getState());
        } else {
            log.info("\nUpdate device id: {}, description: {}, state: {}\n",
                    relayOutput.getId(),
                    relayOutput.getDescription(),
                    relayOutput.getOutputStatus().getState());
        }
    }

    public boolean isRelayOutputStatusOn(RelayOutputScheduleDataDto relayOutputScheduleDataDto) {
        return OutputStatus.ON.equals(relayOutputScheduleDataDto.getRelayOutputStatus());
    }

    public boolean isNotRelayOutputStatusOn(RelayOutputScheduleDataDto relayOutputScheduleDataDto) {
        return !isRelayOutputStatusOn(relayOutputScheduleDataDto);
    }

    public boolean isActualTimeInRange(LocalTime timeStart, LocalTime timeEnd) {
        boolean isAfter = actualTime.isAfter(timeStart);
        boolean isBefore = actualTime.isBefore(timeEnd);
        boolean isOneMinuteRange = timeStart.equals(timeEnd);
        boolean isInRange = isAfter && isBefore || isOneMinuteRange;
        boolean isActualTimeOnEdgeOfRange = actualTime.equals(timeStart) || actualTime.equals(timeEnd);

        return isInRange || isActualTimeOnEdgeOfRange;
    }
}