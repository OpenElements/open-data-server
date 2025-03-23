package com.openelements.opendata.base;

import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface UpdateEntityRepository extends Repository<UpdateEntity, Long> {

    @NonNull
    UpdateEntity save(@NonNull UpdateEntity updateEntity);

    @NonNull Optional<UpdateEntity> findByType(@NonNull @Param("type") String type);
}
