package cz.patyk.solarmaxx.backend.service.scheduled;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
@Profile("!IT")
@RequiredArgsConstructor
public class RelayOutputScheduledService {
    private final RelayOutputService relayOutputService;
    private final RelayAdapterFactory relayAdapterFactory;
    private final Calendar calendar = Calendar.getInstance();
    private final Byte weekDay = (byte) calendar.get(Calendar.DAY_OF_WEEK);

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


    /**
     * Select all relay outputs
     */
    public void setRelayOutputToOn() {

    }

    /**
     *  Method connect to devices via their adapter and check status of their outputs.
     *  Real state of outputs are saved to field {@code deviceOutputStatus} of {@link RelayOutputDataDto}.
     *  Based on compare field {@code outputStatus} and {@code deviceOutputStatus} there are filtered out
     *  these items which are the same state.
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

    private boolean isDeviceStatusDifferentThanRealState(RelayOutputDataDto relayOutputDataDto) {
        return !relayOutputDataDto.getOutputStatus().equals(relayOutputDataDto.getDeviceOutputStatus());
    }

    void raiseLogBasedOnOutputState(RelayOutput relayOutput) {
        if (OutputStatus.NA.equals(relayOutput.getOutputStatus())) {
            log.error("Update device id: {}, description: {}, state: {}",
                    relayOutput.getId(),
                    relayOutput.getDescription(),
                    relayOutput.getOutputStatus().getState());
        } else {
            log.info("Update device id: {}, description: {}, state: {}",
                    relayOutput.getId(),
                    relayOutput.getDescription(),
                    relayOutput.getOutputStatus().getState());
        }
    }
}
