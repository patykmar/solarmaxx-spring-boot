package cz.patyk.solarmaxx.backend.service.scheduled;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelayOutputScheduledService {
    private final RelayOutputService relayOutputService;
    private final RelayAdapterFactory relayAdapterFactory;

    @Scheduled(fixedDelay = 30000)
    void updateOutputStateFromDevices() {
        log.info("Starting method updateOutputStateFromDevices");
        // fetch all outputs
        List<RelayOutputDataDto> relayOutputDataDtos = relayOutputService.getAll();

        long startTime = System.nanoTime();
        // connect to devices/outputs and check actual states
        List<RelayOutput> relayOutputs = relayOutputDataDtos.parallelStream()
                .map(this::checkStatusFromRelay)
                .map(relayOutputService::toEntity)
                .toList();
        long endTime = System.nanoTime();

        long resultTime = endTime - startTime;

        log.info("Collect port status takes: {} seconds", (float) resultTime / 1000000000);

        // update their state into database
        relayOutputs.forEach(relayOutput -> {
            raiseLogBasedOnOutputState(relayOutput);
            relayOutputService.editItemByEntity(relayOutput);
        });

        log.info("Finished method updateOutputStateFromDevices");
    }

    private RelayOutputDataDto checkStatusFromRelay(RelayOutputDataDto relayOutputDataDto) {
        RelayAdapter relayAdapter = relayAdapterFactory.getRelayAdapter(relayOutputDataDto.getRelayTypeEnum());
        return relayAdapter.updateStatusFromRelay(relayOutputDataDto);
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
