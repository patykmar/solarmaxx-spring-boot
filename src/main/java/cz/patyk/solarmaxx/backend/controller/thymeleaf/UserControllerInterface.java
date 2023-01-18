package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.entity.User;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerInterface implements ThymeleafControllerInterface<User, Long> {
    @Override
    public ModelAndView showGrid() {
        return null;
    }

    @Override
    public ModelAndView showForm(Long id) {
        return null;
    }

    @Override
    public String save(User entity) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return null;
    }
}
