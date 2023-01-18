package cz.patyk.solarmaxx.backend.client;

import cz.patyk.solarmaxx.backend.config.RelayClientConfig;
import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@FeignClient(name = "tasmota-client", configuration = RelayClientConfig.class, url = "www.example.com")
public interface TasmotaClient {
    @Headers("Content-Type: application/json")
    @GetMapping("/cm?cmnd=Power{outputId}%20STATUS")
    TasmotaOutputDto getOutputStatusWithSpecificPortObject(
            URI baseUrl,
            @PathVariable("outputId") byte outputId
    );

    @Headers("Content-Type: application/json")
    @GetMapping("/cm?cmnd=Power{outputId}%20{toggle}")
    String setOutputState(
            URI baseUrl,
            @PathVariable("outputId") byte outputId,
            @PathVariable("toggle") String toggle
    );

}
