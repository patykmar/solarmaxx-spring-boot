package cz.patyk.solarmaxx.backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.patyk.solarmaxx.backend.adapter.TasmotaRelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.TasmotaRelayAdapterFactoryTest;
import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
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
 * Disabled annotation. Mocked test are covered in {@link TasmotaRelayAdapterFactoryTest}
 */
class TasmotaClientTest {
    static final String URL = "http://192.168.22.229";

    TasmotaClient tasmotaClient;
    URI specificUrl;

    @BeforeEach
    void setUp() {
        tasmotaClient = Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new SpringFormEncoder())
                .decoder(new JacksonDecoder())
                .contract(new SpringMvcContract())
                .target(TasmotaClient.class, URL);
        specificUrl = URI.create(URL);
    }

    @Test
    @Disabled("Test required connection to real relay. This test is for developing purpose")
    void getPortStatus() {
        TasmotaOutputDto tasmotaOutputDto = tasmotaClient.getOutputStatusWithSpecificPortObject(specificUrl, (byte) 1);

        assertEquals(TasmotaRelayAdapter.TOGGLE_ON, tasmotaOutputDto.getState());
    }

    @Test
    @Disabled("Test required connection to real relay. This test is for developing purpose")
    void toggleTurnOffPort() {
        String response1 = tasmotaClient.setOutputState(specificUrl, (byte) 1, TasmotaRelayAdapter.TOGGLE_OFF);
        String response2 = tasmotaClient.setOutputState(specificUrl, (byte) 2, TasmotaRelayAdapter.TOGGLE_OFF);
        TasmotaOutputDto tasmotaOutputDto1 = parseResponse(response1);
        TasmotaOutputDto tasmotaOutputDto2 = parseResponse(response2);

        assertEquals(TasmotaRelayAdapter.TOGGLE_OFF, tasmotaOutputDto1.getState());
        assertEquals(TasmotaRelayAdapter.TOGGLE_OFF, tasmotaOutputDto2.getState());
    }

    @Test
    @Disabled("Test required connection to real relay. This test is for developing purpose")
    void toggleTurnOnPort() {
        String response1 = tasmotaClient.setOutputState(specificUrl, (byte) 1, TasmotaRelayAdapter.TOGGLE_ON);
        String response2 = tasmotaClient.setOutputState(specificUrl, (byte) 2, TasmotaRelayAdapter.TOGGLE_ON);
        TasmotaOutputDto tasmotaOutputDto1 = parseResponse(response1);
        TasmotaOutputDto tasmotaOutputDto2 = parseResponse(response2);

        assertEquals(TasmotaRelayAdapter.TOGGLE_ON, tasmotaOutputDto1.getState());
        assertEquals(TasmotaRelayAdapter.TOGGLE_ON, tasmotaOutputDto2.getState());
    }


    private TasmotaOutputDto parseResponse(String response) {
        TasmotaOutputDto tasmotaOutputDto;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            tasmotaOutputDto = objectMapper.readValue(response, TasmotaOutputDto.class);
        } catch (JsonProcessingException e) {
            tasmotaOutputDto = TasmotaOutputDto.builder().state("N/A").build();
        }
        return tasmotaOutputDto;
    }
}