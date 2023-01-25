package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.controller.validate.RelayScheduleValidate;
import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.service.RelayScheduleService;
import cz.patyk.solarmaxx.backend.service.RelayService;
import cz.patyk.solarmaxx.backend.service.WeekDayServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/relay/schedule")
public class RelayScheduleController extends ThymeleafAbstractController<RelayScheduleDtoIn, RelayScheduleDtoOut, RelaySchedule, Long> {
    private final PageRequest pageRequest;
    private final RelayService relayService;
    private final RelayScheduleValidate relayScheduleValidate;
    private final WeekDayServices weekDayServices;

    protected RelayScheduleController(
            RelayScheduleService service,
            PageRequest pageRequest,
            RelayService relayService,
            RelayScheduleValidate relayScheduleValidate,
            WeekDayServices weekDayServices
    ) {
        super(service, "redirect:/relay/schedule/list");
        this.pageRequest = pageRequest;
        this.relayService = relayService;
        this.relayScheduleValidate = relayScheduleValidate;
        this.weekDayServices = weekDayServices;
    }

    @Override
    @GetMapping({"", "/list"})
    public ModelAndView showGrid() {
        ModelAndView modelAndView = new ModelAndView("list/relayScheduleList");
        List<RelayScheduleDtoOut> relayScheduleDtoOuts = service.getAll(pageRequest);

        modelAndView.addObject("relayScheduleDtoOuts", relayScheduleDtoOuts);
        return modelAndView;
    }

    @GetMapping({"/edit/{relayId}/{outputId}/{relayScheduleId}", "/new/{relayId}/{outputId}"})
    public ModelAndView showNewItemForm(
            @PathVariable Long relayId,
            @PathVariable Byte outputId,
            @PathVariable(required = false) Long relayScheduleId
    ) {
        ModelAndView modelAndView = new ModelAndView("form/relayScheduleStrictForm");
        Relay relay = relayService.getOneEntity(relayId);

        if (!relayScheduleValidate.validateOutputId(outputId, relay)) {
            log.error("OutputId is not in allowed range");
            return new ModelAndView("redirect:/relay/list");
        }

        RelaySchedule relaySchedule;
        if (Objects.isNull(relayScheduleId)) {
            modelAndView.addObject("h1", "New relay schedule");
            relaySchedule = new RelaySchedule();
            relaySchedule.setOutputId(outputId);
            relaySchedule.setRelay(relay);
        } else {
            modelAndView.addObject("h1", "Edit relay schedule");
            relaySchedule = service.getOneEntity(relayScheduleId);
        }

        redirect = String.format("redirect:/relay/detail/%d", relayId);

        modelAndView.addObject("relayEntity", relay);
        modelAndView.addObject("relayId", relayId);
        modelAndView.addObject("outputId", outputId);
        modelAndView.addObject("relaySchedule", relaySchedule);
        modelAndView.addObject("weekDays", weekDayServices.getWeekDayWithFullName());

        return modelAndView;
    }

    @Override
    @GetMapping({"/edit/{id}", "/new"})
    public ModelAndView showForm(@PathVariable(required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("form/relayScheduleForm");
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

    @GetMapping("/delete/{relayId}/{scheduleId}")
    public String delete(
            @PathVariable Long relayId,
            @PathVariable Long scheduleId
    ) {
        service.deleteItem(scheduleId);

        return String.format("redirect:/relay/detail/%d", relayId);
    }
}
