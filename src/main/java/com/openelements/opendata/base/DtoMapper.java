package com.openelements.opendata.base;

import java.util.List;
import org.jspecify.annotations.NonNull;

public interface DtoMapper<D extends DTO, E extends AbstractEntity> {

    E dtoToEntity(D dto);

    D entityToDto(E entity);

    List<E> dtoToEntity(Iterable<D> dtos);

    List<D> entityToDto (Iterable<E> entities);
}
