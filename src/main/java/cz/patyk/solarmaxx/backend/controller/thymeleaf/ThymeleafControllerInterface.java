package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Specify methods for basic CRUD operations
 *
 * @param <E> entity object
 * @param <D> type of ID
 */
public interface ThymeleafControllerInterface<E, D> {
    @GetMapping({"", "/list"})
    ModelAndView showGrid();

    @GetMapping({"/edit/{id}", "/new"})
    ModelAndView showForm(@PathVariable(required = false) D id);

    @PostMapping("/save")
    String save(@ModelAttribute E entity);

    @GetMapping("/delete/{id}")
    String delete(@PathVariable D id);
}
