package com.openelements.opendata.base;

import com.openelements.opendata.base.db.AbstractEntity;
import com.openelements.opendata.base.db.I18nString;
import java.util.List;
import org.mapstruct.MappingTarget;

public interface DtoMapper<D extends DTO, E extends AbstractEntity> {

    E dtoToEntity(D dto);

    D entityToDto(E entity);

    List<E> dtoToEntity(Iterable<D> dtos);

    List<D> entityToDto(Iterable<E> entities);

    E updateEntityFromDto(D dto, @MappingTarget E entity);

    E updateEntity(E updated, @MappingTarget E toUpdate);

    default String convertI18nString(I18nString i18nString) {
        if (i18nString == null) {
            return null;
        }
        if (!i18nString.isResolved()) {
            throw new IllegalArgumentException("I18nString is not resolved");
        }
        return i18nString.getValue();
    }

    default I18nString map(String value) {
        throw new UnsupportedOperationException("Mapping in the direction String->I18nString is not supported");
    }
}
