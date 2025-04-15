package com.openelements.opendata.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityBasedService<T extends DTO, E extends AbstractEntity> extends AbstractService<T> {

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

    @NonNull
    public List<T> getAll() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<E> cq = cb.createQuery(entityClass);
        final Root<E> rootEntry = cq.from(entityClass);
        final CriteriaQuery<E> all = cq.select(rootEntry);
        final TypedQuery<E> allQuery = em.createQuery(all);
        return getMapper().entityToDto(allQuery.getResultList());
    }

    protected Optional<E> findEntityByUuid(String uuid) {
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
    public void updateDatabase(@NonNull final List<T> dtos) {
        Objects.requireNonNull(dtos, "dtos cannot be null").stream()
                .forEach(dto -> {
                    if (dto.uuid() == null) {
                        throw new IllegalArgumentException("UUID cannot be null");
                    }
                    findEntityByUuid(dto.uuid()).ifPresentOrElse(entity -> {
                        final E updatedEntity = getMapper().updateEntityFromDto(dto, entity);
                        getEntityManager().merge(updatedEntity);
                    }, () -> {
                        getEntityManager().persist(getMapper().dtoToEntity(dto));
                    });
                });
    }

    private boolean containsWithUUID(@NonNull String uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        return getAll().stream().anyMatch(dto -> dto.uuid().equals(uuid));
    }
}
