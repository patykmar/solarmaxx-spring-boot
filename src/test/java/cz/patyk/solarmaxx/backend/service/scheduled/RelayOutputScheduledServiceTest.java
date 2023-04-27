package cz.patyk.solarmaxx.backend.service.scheduled;

import cz.patyk.solarmaxx.backend.OutputDtoConstants;
import cz.patyk.solarmaxx.backend.adapter.ShellyProRelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.TasmotaRelayAdapter;
import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.client.TasmotaClient;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayOutputMapper;
import cz.patyk.solarmaxx.backend.repository.RelayOutputRepository;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import cz.patyk.solarmaxx.backend.service.ErrorHandleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
class RelayOutputScheduledServiceTest {

    RelayOutputService relayOutputService;
    @Mock
    TasmotaClient tasmotaClient;
    @Mock
    ShellyProClient shellyProClient;
    @Mock
    RelayOutputRepository relayOutputRepository;
    @Mock
    RelayRepository relayRepository;
    RelayOutputMapper relayOutputMapper = Mappers.getMapper(RelayOutputMapper.class);
    ErrorHandleService<Long> errorHandleService = new ErrorHandleService<>();
    RelayAdapterFactory relayAdapterFactory;
    RelayOutputScheduledService relayOutputScheduledService;

    @BeforeEach
    void setUp() {
        relayAdapterFactory = new RelayAdapterFactory(
                new TasmotaRelayAdapter(tasmotaClient),
                new ShellyProRelayAdapter(new OutputStatusMapper(), shellyProClient)
        );

        ReflectionTestUtils.setField(relayOutputMapper, "relayRepository", relayRepository);

        relayOutputService = new RelayOutputService(relayAdapterFactory, relayOutputRepository, relayOutputMapper, errorHandleService);

        relayOutputScheduledService = new RelayOutputScheduledService(relayOutputService, relayAdapterFactory);
    }

    /**
     * Provided output ports with the same state os are on database
     * Expected there no invoke database update.
     */
    @Test
    void updateOutputStateTasmotaPortOnToOn() {
        Mockito.when(relayOutputRepository.findAll())
                .thenReturn(RelayOutputEntityConstants.RELAY_OUTPUT_TASMOTA_LIST_ON);

        Mockito.when(tasmotaClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
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
        Mockito.when(shellyProClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
                .thenReturn(OutputDtoConstants.SHELLY_PRO_STATUS_TRUE);

        // method should update state of output in database
        relayOutputScheduledService.updateOutputStateFromDevices();

        // verify if the save method of repository was calling 4x
        Mockito.verify(relayOutputRepository, Mockito.times(4))
                .save(any());
    }


}