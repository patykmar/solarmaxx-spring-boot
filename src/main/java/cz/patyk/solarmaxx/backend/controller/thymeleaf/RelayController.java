package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.dto.in.RelayDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.service.RelayService;
import cz.patyk.solarmaxx.backend.service.RelayTypeService;
import cz.patyk.solarmaxx.backend.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/relay")
public class RelayController extends ThymeleafAbstractController<RelayDtoIn, RelayDtoOut, Relay, Long> {
    private final RelayTypeService relayTypeService;
    private final UserService userService;

    public RelayController(RelayService relayService, RelayTypeService relayTypeService, UserService userService) {
        super(relayService, "redirect:/relay/list");
        this.relayTypeService = relayTypeService;
        this.userService = userService;
    }

    @GetMapping({"", "/list"})
    public ModelAndView showGrid() {
        ModelAndView modelAndView = new ModelAndView("list/relay");
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<RelayDtoOut> relayServiceAll = service.getAll(pageRequest);

        modelAndView.addObject("relays", relayServiceAll);
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView showDetail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("detail/relay");
        RelayDtoOut relayDtoOut = service.getOne(id);

        modelAndView.addObject("h1", relayDtoOut.getName());
        modelAndView.addObject("relayDtoOut", relayDtoOut);
        return modelAndView;
    }

    @GetMapping({"/edit/{id}", "/new"})
    public ModelAndView showForm(@PathVariable(required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("form/relay");
        Relay relayEntity;
        if (Objects.isNull(id)) {
            modelAndView.addObject("h1", "New relay");
            relayEntity = new Relay();
        } else {
            modelAndView.addObject("h1", "Edit relay");
            relayEntity = service.getOneEntity(id);
        }
        modelAndView.addObject("relayTypeService", relayTypeService);
        modelAndView.addObject("userService", userService);
        modelAndView.addObject("relayEntity", relayEntity);
        return modelAndView;
    }

}
