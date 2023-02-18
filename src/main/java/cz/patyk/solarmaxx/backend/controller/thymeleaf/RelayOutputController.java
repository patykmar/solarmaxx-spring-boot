package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.exceptions.ApplicationException;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/relay-output")
public class RelayOutputController {
    private final RelayOutputService relayOutputService;

    @GetMapping("/toggle/{relayId}/{relayOutputId}/{toggle}")
    public String toggleOutput(
            @PathVariable Long relayId,
            @PathVariable Long relayOutputId,
            @PathVariable Boolean toggle
    ) {
        try {
            relayOutputService.toggleOutput(relayOutputId, toggle);
        } catch (ApplicationException e) {
            return "redirect:/relay/list";
        }
        return "redirect:/relay/detail/" + relayId;
    }

}
