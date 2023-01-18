package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.entity.Tariff;
import org.springframework.web.servlet.ModelAndView;

public class TariffControllerInterface implements ThymeleafControllerInterface<Tariff, Long> {
    @Override
    public ModelAndView showGrid() {
        return null;
    }

    @Override
    public ModelAndView showForm(Long id) {
        return null;
    }

    @Override
    public String save(Tariff entity) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return null;
    }
}
