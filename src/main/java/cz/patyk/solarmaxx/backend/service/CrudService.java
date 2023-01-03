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

    List<O> getAll();

    O getOne(Long id);

    E getOneEntity(Long id);

    O newItem(I dtoIn);

    O newItemByEntity(E newEntity);

    O editItem(I dtoIn, Long id);

    O editItemByEntity(E entity);

    void deleteItem(Long id);
}
