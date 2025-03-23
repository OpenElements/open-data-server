package com.openelements.opendata.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<T extends DTO, E extends AbstractEntity> {

    private final DtoMapper<T, E> mapper;

    private final Class<E> entityClass;

    public AbstractService(@NonNull final Class<? extends DtoMapper<T, E>> mapperClass, @NonNull final Class<E> entityClass) {
        Objects.requireNonNull(mapperClass);
        this.mapper = Mappers.getMapper(mapperClass);
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
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> rootEntry = cq.from(entityClass);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = em.createQuery(all);
        return getMapper().entityToDto(allQuery.getResultList());
    }

    @Transactional
    public void updateDatabase(@NonNull final List<T> dtos) {
       dtos.stream()
            .map(dtoObjects -> getMapper().dtoToEntity(dtoObjects))
            .forEach(getEntityManager()::persist);
    }
}
