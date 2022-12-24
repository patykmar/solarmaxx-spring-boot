package cz.patyk.solarmaxx.backend.mapper;

/**
 * Basic mapper methods template
 *
 * @param <E> entity type of object
 * @param <I> DtoIn type of object where is relation specify as ID
 * @param <D> DtoOut type of object where relations specify by DTO out objects
 */
public interface BasicMapper<E, I, D> {
    E toEntity(I dtoIn);

    D toDtoOut(E entity);
}
