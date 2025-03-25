package com.openelements.opendata.pullrequests;

import com.openelements.opendata.base.AbstractEntityBasedService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PullRequestService extends AbstractEntityBasedService<PullRequestDTO, PullRequest> {

    @PersistenceContext
    private EntityManager entityManager;

    public PullRequestService() {
        super(PullRequestMapper.class, PullRequest.class);
    }

    @Override
    protected @NonNull EntityManager getEntityManager() {
        return entityManager;
    }
}
