package cz.patyk.solarmaxx.backend.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Specify methods for basic CRUD operations
 * @param <I> input DTO object
 * @param <R> return DTO object
 * @param <E> entity object
 */
public interface CrudService<I, R, E> {
    List<R> getAll(Pageable pageable);
    R getOne(Long id);
    E getOneEntity(Long id);
    R newItem(I dtoIn);
    R editItem(I dtoIn, Long id);
    void deleteItem(Long id);
}
