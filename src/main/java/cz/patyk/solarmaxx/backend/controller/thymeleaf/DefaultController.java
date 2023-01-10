package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String entryPointOfApplication() {
        return "redirect:/relay/list";
    }
}
