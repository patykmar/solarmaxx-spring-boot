package cz.patyk.solarmaxx.backend.controller.thymeleaf;

import cz.patyk.solarmaxx.backend.entity.IEntity;
import cz.patyk.solarmaxx.backend.service.CrudService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

/**
 * Specify methods for basic CRUD operations
 *
 * @param <I> input DTO object
 * @param <O> output DTO out object
 * @param <E> entity object
 * @param <D> type of ID
 */
public abstract class ThymeleafAbstractController<I, O, E extends IEntity<D>, D extends Number> implements ThymeleafControllerInterface<E, D> {
    protected CrudService<I, O, E> service;
    protected String redirect;

    protected ThymeleafAbstractController(
            CrudService<I, O, E> service,
            @NonNull String redirect
    ) {
        this.service = service;
        this.redirect = redirect;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute E relay) {
        if (Objects.isNull(relay.getId())) {
            // new item
            service.newItemByEntity(relay);
        } else {
            // edit item
            service.editItemByEntity(relay);
        }
        return redirect;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteItem(id);
        return redirect;
    }
}
