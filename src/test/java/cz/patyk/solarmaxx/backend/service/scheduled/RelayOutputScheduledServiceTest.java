package cz.patyk.solarmaxx.backend.service.scheduled;

import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.OutputDtoConstants;
import cz.patyk.solarmaxx.backend.adapter.ShellyProRelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.TasmotaRelayAdapter;
import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.client.TasmotaClient;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import cz.patyk.solarmaxx.backend.mapper.RelayOutputScheduleMapper;
import cz.patyk.solarmaxx.backend.mapper.WeekDayMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayOutputMapper;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import cz.patyk.solarmaxx.backend.repository.RelayOutputRepository;
import cz.patyk.solarmaxx.backend.repository.RelayOutputScheduleRepository;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import cz.patyk.solarmaxx.backend.service.ErrorHandleService;
import cz.patyk.solarmaxx.backend.service.RelayOutputScheduleService;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import cz.patyk.solarmaxx.constants.RelayOutputEntityConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;

import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
class RelayOutputScheduledServiceTest {
    @Mock
    TasmotaClient tasmotaClient;
    @Mock
    ShellyProClient shellyProClient;
    @Mock
    RelayOutputRepository relayOutputRepository;
    @Mock
    RelayOutputScheduleRepository relayOutputScheduleRepository;
    RelayAdapterFactory relayAdapterFactory;
    RelayOutputScheduledService relayOutputScheduledService;

    @BeforeEach
    void setUp() {
        relayAdapterFactory = new RelayAdapterFactory(
                new TasmotaRelayAdapter(tasmotaClient),
                new ShellyProRelayAdapter(new OutputStatusMapper(), shellyProClient)
        );
        ErrorHandleService<Long> errorHandleService = new ErrorHandleService<>();

        RelayRepository relayRepository = Mockito.mock(RelayRepository.class);
        RelayOutputMapper relayOutputMapper = Mappers.getMapper(RelayOutputMapper.class);
        ReflectionTestUtils.setField(relayOutputMapper, "relayRepository", relayRepository);
        RelayOutputService relayOutputService = new RelayOutputService(relayAdapterFactory, relayOutputRepository, relayOutputMapper, errorHandleService);

        WeekDayMapper weekDayMapper = new WeekDayMapper(new WeekDayModel());
        RelayOutputScheduleMapper relayOutputScheduleMapper = Mappers.getMapper(RelayOutputScheduleMapper.class);
        ReflectionTestUtils.setField(relayOutputScheduleMapper, "weekDayMapper", weekDayMapper);
        ReflectionTestUtils.setField(relayOutputScheduleMapper, "relayOutputService", relayOutputService);

        RelayOutputScheduleService relayOutputScheduleService = new RelayOutputScheduleService(relayOutputScheduleRepository, relayOutputScheduleMapper, errorHandleService, relayOutputScheduleRepository);

        relayOutputScheduledService = new RelayOutputScheduledService(relayOutputService, relayAdapterFactory, relayOutputScheduleService);
    }

    /**
     * Provided output ports with the same state os are on database
     * Expected there no invoke database update.
     */
    @Test
    void updateOutputStateTasmotaPortOnToOn() {
        Mockito.when(relayOutputRepository.findAll())
                .thenReturn(RelayOutputEntityConstants.RELAY_OUTPUT_TASMOTA_LIST_ON);

        Mockito.when(tasmotaClient.getOutputStatusWithSpecificPortObject(any(URI.class), anyByte()))
                .thenReturn(OutputDtoConstants.TASMOTA_OUTPUT_DTO_ON);

        relayOutputScheduledService.updateOutputStateFromDevices();

        Mockito.verify(relayOutputRepository, Mockito.times(0))
                .save(any());
    }


    /**
     * Provided outputs with state OFF and when check status on device you receive state ON
     * Expected invoke 4x calling repository save.
     */
    @Test
    void updateOutputStateShellyProPortsOffToOn() {

        // provide relays output with status OFF
        Mockito.when(relayOutputRepository.findAll())
                .thenReturn(RelayOutputEntityConstants.RELAY_OUTPUT_SHELLY_LIST_OFF);

        // skip validation on service layer
        Mockito.when(relayOutputRepository.existsById(anyLong()))
                .thenReturn(true);

        // from device adapter you receive, that's outputs are ON
        Mockito.when(shellyProClient.getOutputStatusWithSpecificPortObject(any(URI.class), anyByte()))
                .thenReturn(OutputDtoConstants.SHELLY_PRO_STATUS_TRUE);

        // method should update state of output in database
        relayOutputScheduledService.updateOutputStateFromDevices();

        // verify if the save method of repository was calling 4x
        Mockito.verify(relayOutputRepository, Mockito.times(4))
                .save(any());
    }

    @Test
    void collectAllRelayOutputsWhichShouldBeUpBySchedulerForConcreteDayOfWeekTest() {

        Mockito.when(relayOutputScheduleRepository.findAllByDayNumber(anyByte()))
                .thenReturn(EntityConstants.RELAY_OUTPUT_SCHEDULE_ENTITIES);

        relayOutputScheduledService.collectAllRelayOutputsWhichShouldBeUpBySchedulerForConcreteDayOfWeek();

        Mockito.verify(relayOutputRepository, Mockito.times(4))
                .save(any());

    }


}