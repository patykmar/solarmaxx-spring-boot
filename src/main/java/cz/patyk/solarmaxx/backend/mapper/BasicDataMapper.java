package cz.patyk.solarmaxx.backend.mapper;

/**
 * Basic mapper methods template
 *
 * @param <E> Entity type
 * @param <D> DTO type
 * @param <A> dAta DTO type which hase more detail
 */
public interface BasicDataMapper<E, D, A> {
    E dtoToEntity(D dto);

    E dtoDataToEntity(A dataDto);

    A entityToDataDto(E entity);

    D entityToDto(E entity);
}
