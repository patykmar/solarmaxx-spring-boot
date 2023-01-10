package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.service.RelayScheduleService;
import cz.patyk.solarmaxx.backend.service.RelayService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/relay/schedule")
public class RelayScheduleController extends ThymeleafAbstractController<RelayScheduleDtoIn, RelayScheduleDtoOut, RelaySchedule, Long> {
    private final PageRequest pageRequest;
    private final RelayService relayService;

    protected RelayScheduleController(RelayScheduleService service, PageRequest pageRequest, RelayService relayService) {
        super(service, "redirect:/relay/schedule/list");
        this.pageRequest = pageRequest;
        this.relayService = relayService;
    }

    @Override
    @GetMapping({"", "/list"})
    public ModelAndView showGrid() {
        ModelAndView modelAndView = new ModelAndView("list/relaySchedule");
        List<RelayScheduleDtoOut> relayScheduleDtoOuts = service.getAll(pageRequest);

        modelAndView.addObject("relayScheduleDtoOuts", relayScheduleDtoOuts);
        return modelAndView;
    }

    @Override
    @GetMapping({"/edit/{id}", "/new"})
    public ModelAndView showForm(@PathVariable(required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("form/relaySchedule");
        RelaySchedule relaySchedule;
        if (Objects.isNull(id)) {
            modelAndView.addObject("h1", "New relay schedule");
            relaySchedule = new RelaySchedule();
        } else {
            modelAndView.addObject("h1", "Edit relay schedule");
            relaySchedule = service.getOneEntity(id);
        }
        modelAndView.addObject("relaySchedule", relaySchedule);
        modelAndView.addObject("relayScheduleService", service);
        modelAndView.addObject("relayService", relayService);
        return modelAndView;
    }

}
