package com.openelements.opendata.base;

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

public abstract class AbstractProviderService<T extends DTO> implements Supplier<List<T>> {

    private final static ZonedDateTime MIN_TIME = Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC);

    private final Class<T> dtoClass;

    private final UpdateEntityRepository updateRepository;

    public AbstractProviderService(@NonNull final Class<T> dtoClass, @NonNull final UpdateEntityRepository updateRepository) {
        this.dtoClass = Objects.requireNonNull(dtoClass);
        this.updateRepository = Objects.requireNonNull(updateRepository);
    }

    @Transactional
    @Override
    @NonNull
    public List<T> get() {
        final String type = dtoClass.getSimpleName();
        final Optional<UpdateEntity> optionalUpdateEntity = updateRepository.findByType(type);
        final ZonedDateTime lastUpdateTime = optionalUpdateEntity
                .map(updateEntity -> updateEntity.getLastUpdate())
                .orElse(MIN_TIME);
        final List<T> result = getAvailableData(lastUpdateTime);
        final UpdateEntity entity = optionalUpdateEntity.orElseGet(() -> {
            final UpdateEntity updateEntity = new UpdateEntity();
            updateEntity.setType(type);
            updateEntity.setCreatedAt(ZonedDateTime.now());
            updateEntity.setUuid(UUID.randomUUID().toString());
            return updateEntity;
        });
        entity.setLastUpdate(ZonedDateTime.now());
        updateRepository.save(entity);
        return result;
    }

    @NonNull
    protected abstract List<T> getAvailableData(@NonNull ZonedDateTime lastUpdateTime);
}
