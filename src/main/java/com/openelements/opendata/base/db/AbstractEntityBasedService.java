package com.openelements.opendata.base.db;

import static org.slf4j.LoggerFactory.getLogger;

import com.openelements.opendata.base.AbstractService;
import com.openelements.opendata.base.DTO;
import com.openelements.opendata.base.DtoMapper;
import com.openelements.opendata.base.Language;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityBasedService<T extends DTO, E extends AbstractEntity> extends AbstractService<T> {

    private final Logger log = getLogger(getClass());

    private final DtoMapper<T, E> mapper;

    private final Class<E> entityClass;

    public AbstractEntityBasedService(@NonNull final Class<? extends DtoMapper<T, E>> mapperClass,
            @NonNull final Class<E> entityClass) {
        Objects.requireNonNull(mapperClass);
        this.mapper = Mappers.getMapper(mapperClass);
        Objects.requireNonNull(mapper, "mapper cannot be null");
        this.entityClass = Objects.requireNonNull(entityClass);
    }

    @NonNull
    protected abstract EntityManager getEntityManager();

    @NonNull
    protected final DtoMapper<T, E> getMapper() {
        return mapper;
    }

    protected E resolveI18nStrings(@NonNull final E entity, @NonNull final Language language) {
        Objects.requireNonNull(entity, "entity cannot be null");
        Objects.requireNonNull(language, "language cannot be null");

        Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(I18nString.class))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        final I18nString i18nString = (I18nString) field.get(entity);
                        if (i18nString != null) {
                            i18nString.resolve(language);
                        }
                    } catch (IllegalAccessException e) {
                        log.error("Error accessing field: {}", field.getName(), e);
                    }
                });
        return entity;
    }

    @NonNull
    public List<T> getAll(@NonNull final Language language) {
        Objects.requireNonNull(language, "language cannot be null");
        return getAllEntities().stream()
                .map(entity -> resolveI18nStrings(entity, language))
                .map(entity -> getMapper().entityToDto(entity))
                .toList();
    }

    private List<E> getAllEntities() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<E> cq = cb.createQuery(entityClass);
        final Root<E> rootEntry = cq.from(entityClass);
        final CriteriaQuery<E> all = cq.select(rootEntry);
        final TypedQuery<E> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    private Optional<E> findEntityByUuid(String uuid) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<E> query = cb.createQuery(entityClass);
        final Root<E> root = query.from(entityClass);
        query.select(root).where(cb.equal(root.get("uuid"), uuid));

        try {
            return Optional.of(getEntityManager().createQuery(query).getSingleResult());
        } catch (final NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void updateDatabase(@NonNull final List<E> entities) {
        Objects.requireNonNull(entities, "entities cannot be null").stream()
                .forEach(entity -> {
                    if (entity.getUuid() == null) {
                        throw new IllegalArgumentException("UUID cannot be null");
                    }
                    findEntityByUuid(entity.getUuid()).ifPresentOrElse(storedEntity -> {
                        log.info("Updating entity with UUID: {}", entity.getUuid());
                        final E updatedEntity = getMapper().updateEntity(entity, storedEntity);
                        getEntityManager().merge(updatedEntity);
                    }, () -> {
                        log.info("Creating new entity with UUID: {}", entity.getUuid());
                        getEntityManager().persist(entity);
                    });
                });
    }

    @Override
    public long getCount() {
        final EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<E> root = cq.from(entityClass);
        cq.select(cb.count(root));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
