package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.service.RelayService;
import cz.patyk.solarmaxx.backend.service.RelayTypeService;
import cz.patyk.solarmaxx.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/relay")
public class RelayController {
    private final RelayService relayService;
    private final RelayTypeService relayTypeService;
    private final UserService userService;

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

    @GetMapping({"/edit", "/new"})
    public ModelAndView showForm(@RequestParam(required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("form/relay");
        Relay relayEntity;
        if (Objects.isNull(id)) {
            modelAndView.addObject("h1", "New relay");
            relayEntity = new Relay();
        } else {
            modelAndView.addObject("h1", "Edit relay");
            relayEntity = relayService.getOneEntity(id);
        }
        modelAndView.addObject("relayTypeService", relayTypeService);
        modelAndView.addObject("userService", userService);
        modelAndView.addObject("relayEntity", relayEntity);
        return modelAndView;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Relay relay) {
        if(Objects.isNull(relay.getId())){
            // new item
            relayService.newItemByEntity(relay);
        } else {
            // edit item
            relayService.editItemByEntity(relay);
        }
        return "redirect:/relay/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        relayService.deleteItem(id);
        return "redirect:/relay/list";
    }
}
