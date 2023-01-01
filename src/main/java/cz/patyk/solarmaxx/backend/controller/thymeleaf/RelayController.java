package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.service.RelayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RelayController {
    private final RelayService relayService;

    @GetMapping({"/", "/relay-list"})
    public ModelAndView showGrid() {
        ModelAndView modelAndView = new ModelAndView("list/relay");
        PageRequest pageRequest = PageRequest.of(0, 10);
        modelAndView.addObject("relays", relayService.getAll(pageRequest));
        return modelAndView;
    }
}
