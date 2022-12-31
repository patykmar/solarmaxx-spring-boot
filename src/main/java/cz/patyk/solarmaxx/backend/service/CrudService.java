package cz.patyk.solarmaxx.backend.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Specify methods for basic CRUD operations
 *
 * @param <I> input DTO object
 * @param <O> DTO out object
 * @param <E> entity object
 */
public interface CrudService<I, O, E> {
    List<O> getAll(Pageable pageable);

    O getOne(Long id);

    E getOneEntity(Long id);

    O newItem(I dtoIn);

    O editItem(I dtoIn, Long id);

    void deleteItem(Long id);
}
