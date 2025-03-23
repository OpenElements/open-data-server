package com.openelements.opendata.base;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface EntityRepository<E extends AbstractEntity> extends Repository<E, Long> {

    @NonNull Optional<E> findByUuid(@NonNull @Param("uuid") String uuid);

    @NonNull List<E> findAll();
}
