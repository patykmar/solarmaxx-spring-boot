package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.in.IDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.IDtoOut;
import cz.patyk.solarmaxx.backend.entity.IEntity;
import cz.patyk.solarmaxx.backend.mapper.BasicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Specify methods for basic CRUD operations
 *
 * @param <I> input DTO object
 * @param <O> return DTO object
 * @param <E> entity object
 * @param <D> Datatype of entity ID
 */
@RequiredArgsConstructor
public abstract class AbstractCrudService<I extends IDtoIn, O extends IDtoOut, E extends IEntity<D>, D extends Number> implements CrudService<I, O, E> {
    protected final JpaRepository<E, D> repository;
    protected final BasicMapper<E, I, O> mapper;
    protected final ErrorHandleService<D> errorHandleService;
    protected final String notFoundErrorMessage;

    public List<O> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDtoOut)
                .toList();
    }

    public O getOne(D id) {
        return mapper.toDtoOut(getOneEntity(id));
    }

    public E getOneEntity(D id) {
        return repository.findById(id)
                .orElseThrow(() -> errorHandleService.handleNotFoundError(id, notFoundErrorMessage));
    }

    public O newItem(I dtoIn) {
        E entity = mapper.toEntity(dtoIn);
        return mapper.toDtoOut(repository.save(entity));
    }

    public O editItem(I dtoIn, D id) {
        checkIfExistEntity(id);
        E entity = mapper.toEntity(dtoIn);
        entity.setId(id);
        return mapper.toDtoOut(repository.save(entity));
    }

    public void deleteItem(D id) {
        checkIfExistEntity(id);
        repository.deleteById(id);
    }

    protected void checkIfExistEntity(D id) {
        if (!repository.existsById(id)) {
            throw errorHandleService.handleNotFoundError(id, notFoundErrorMessage);
        }
    }

}
