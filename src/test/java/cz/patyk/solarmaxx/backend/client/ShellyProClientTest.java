package cz.patyk.solarmaxx.backend.client;

import cz.patyk.solarmaxx.backend.adapter.ShellyProRelayAdapterTest;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProStatusOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProToggleOutputDto;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import feign.Feign;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tested relay client to real relay. This test is only for troubleshoot purpose.
 * By default, test methods are disabled, because cannot guarantee connection to
 * real relay. When you want to test it, please update URL constant and removed
 * Disabled annotation. Mocked test are covered in {@link ShellyProRelayAdapterTest}
 */
class ShellyProClientTest {
    static final String URL = "http://192.168.22.234";
    OutputStatusMapper outputStatusMapper = new OutputStatusMapper();
    ShellyProClient shellyProClient;
    URI specificUrl;

    @BeforeEach
    void setUp() {
        shellyProClient = Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new SpringFormEncoder())
                .decoder(new JacksonDecoder())
                .target(ShellyProClient.class, URL);
        specificUrl = URI.create(URL);
    }

    @Test
    @Disabled("Test required connection to real relay. This test is for developing purpose")
    void getPortStatus() {
        ShellyProStatusOutputDto shellyProStatusOutputDto = shellyProClient.getOutputStatusWithSpecificPortObject(specificUrl, (byte) 1);
        assertEquals(OutputStatus.OFF, parseStatusResponseAndUpdateState(shellyProStatusOutputDto));
    }

    @Test
    @Disabled("Test required connection to real relay. This test is for developing purpose")
    void setToggleStatus() {
        ShellyProToggleOutputDto shellyProToggleOutputDto = shellyProClient.setOutputState(specificUrl, (byte) 1);
        assertEquals(OutputStatus.ON, parseToogleResponseAndUpdateState(shellyProToggleOutputDto));
    }

    private OutputStatus parseStatusResponseAndUpdateState(ShellyProStatusOutputDto shellyProStatusOutputDto) {
        return outputStatusMapper.toOutputStatus(shellyProStatusOutputDto.getState());
    }

    private OutputStatus parseToogleResponseAndUpdateState(ShellyProToggleOutputDto proToggleOutputDto) {
        return outputStatusMapper.toOutputStatusReverse(proToggleOutputDto.getState());
    }
}
