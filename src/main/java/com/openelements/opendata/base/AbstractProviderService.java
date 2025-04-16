package com.openelements.opendata.base;

import com.openelements.opendata.base.db.AbstractEntity;
import com.openelements.opendata.base.db.UpdateEntity;
import com.openelements.opendata.base.db.UpdateEntityRepository;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractProviderService<E extends AbstractEntity> implements Supplier<List<E>> {

    private final static ZonedDateTime MIN_TIME = Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC);

    private final Class<E> entityClass;

    private final UpdateEntityRepository updateRepository;

    public AbstractProviderService(@NonNull final Class<E> entityClass,
            @NonNull final UpdateEntityRepository updateRepository) {
        this.entityClass = Objects.requireNonNull(entityClass);
        this.updateRepository = Objects.requireNonNull(updateRepository);
    }

    @Transactional
    @Override
    @NonNull
    public List<E> get() {
        final ZonedDateTime startOfUpdate = ZonedDateTime.now();
        final String type = entityClass.getSimpleName();
        final Optional<UpdateEntity> optionalUpdateEntity = updateRepository.findByType(type);
        final ZonedDateTime lastUpdateTime = optionalUpdateEntity
                .map(updateEntity -> updateEntity.getLastUpdate())
                .orElse(MIN_TIME);
        final List<E> result = getAvailableData(lastUpdateTime).stream()
                .filter(entity -> !getUUIDBlacklist().contains(entity.getUuid()))
                .toList();
        final UpdateEntity entity = optionalUpdateEntity.orElseGet(() -> {
            final UpdateEntity updateEntity = new UpdateEntity();
            updateEntity.setType(type);
            updateEntity.setCreatedAt(ZonedDateTime.now());
            updateEntity.setUuid(UUID.randomUUID().toString());
            return updateEntity;
        });
        entity.setLastUpdate(startOfUpdate);
        updateRepository.save(entity);
        return result;
    }

    @NonNull
    protected List<String> getUUIDBlacklist() {
        return List.of();
    }

    @NonNull
    protected abstract List<E> getAvailableData(@NonNull ZonedDateTime lastUpdateTime);
}
