package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.service.RelayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/relay")
@RequiredArgsConstructor
public class RelayController {
    private final RelayService relayService;

    @GetMapping({"", "/list"})
    public ModelAndView showGrid() {
        ModelAndView modelAndView = new ModelAndView("list/relay");
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<RelayDtoOut> relayServiceAll = relayService.getAll(pageRequest);

        modelAndView.addObject("relays", relayServiceAll);
        return modelAndView;
    }

    @GetMapping("/detail")
    public ModelAndView showDetail(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("detail/relay");
        RelayDtoOut relayDtoOut = relayService.getOne(id);

        modelAndView.addObject("h1", relayDtoOut.getName());
        modelAndView.addObject("relayDtoOut", relayDtoOut);
        return modelAndView;
    }
//
//    @GetMapping({"/edit", "/new"})
//    public ModelAndView showForm(@RequestParam(required = false) Long id) {
//        ModelAndView modelAndView = new ModelAndView("form/relay");
//    }
}
