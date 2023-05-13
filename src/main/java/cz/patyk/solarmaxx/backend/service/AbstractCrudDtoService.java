package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.DtoInterface;
import cz.patyk.solarmaxx.backend.dto.data.DtoDataInterface;
import cz.patyk.solarmaxx.backend.entity.IEntity;
import cz.patyk.solarmaxx.backend.mapper.BasicDataMapper;
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
public abstract class AbstractCrudDtoService<I extends DtoInterface, O extends DtoDataInterface, E extends IEntity<D>, D extends Number> implements CrudService<I, O, E> {
    protected final JpaRepository<E, D> repository;
    protected final BasicDataMapper<E, I, O> mapper;
    protected final ErrorHandleService<D> errorHandleService;
    protected final String notFoundErrorMessage;

    public List<O> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToDataDto)
                .toList();
    }

    @Override
    public List<O> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToDataDto)
                .toList();
    }

    public O getOne(D id) {
        return mapper.entityToDataDto(getOneEntity(id));
    }

    public E getOneEntity(D id) {
        return repository.findById(id)
                .orElseThrow(() -> errorHandleService.handleNotFoundError(id, notFoundErrorMessage));
    }

    public O newItem(I dtoIn) {
        E entity = mapper.dtoToEntity(dtoIn);
        return newItemByEntity(entity);
    }

    @Override
    public O newItemByEntity(E newEntity) {
        return mapper.entityToDataDto(repository.save(newEntity));
    }

    public O editItem(I dtoIn, D id) {
        E entity = mapper.dtoToEntity(dtoIn);
        entity.setId(id);
        return editItemByEntity(entity);
    }

    @Override
    public O editItemByEntity(E entity) {
        checkIfExistEntity(entity.getId());
        return mapper.entityToDataDto(repository.save(entity));
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
