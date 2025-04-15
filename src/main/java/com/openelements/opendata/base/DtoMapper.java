package com.openelements.opendata.base;

import java.util.List;
import org.mapstruct.MappingTarget;

public interface DtoMapper<D extends DTO, E extends AbstractEntity> {

    E dtoToEntity(D dto);

    D entityToDto(E entity);

    List<E> dtoToEntity(Iterable<D> dtos);

    List<D> entityToDto(Iterable<E> entities);

    E updateEntityFromDto(D dto, @MappingTarget E entity);

    E updateEntity(E updated, @MappingTarget E toUpdate);
}
