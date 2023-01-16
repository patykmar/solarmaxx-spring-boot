package cz.patyk.solarmaxx.backend.client;

import feign.Headers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

public interface ShellyProClient {
    @Headers("Content-Type: application/json")
    @GetMapping("/rpc/Switch.GetStatus?id={outputId}")
    String getOutputStatusWithSpecificPortObject(URI baseUrl, @PathVariable("outputId") byte outputId);

    @Headers("Content-Type: application/json")
    @GetMapping("/rpc/Switch.Toggle?id={outputId}")
    String setOutputState(URI baseUrl, @PathVariable("outputId") byte outputId);
}
