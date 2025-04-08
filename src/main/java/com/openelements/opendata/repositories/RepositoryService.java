package com.openelements.opendata.repositories;

import com.openelements.opendata.base.AbstractService;
import com.openelements.opendata.pullrequests.PullRequestService;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService extends AbstractService<RepositoryDTO> {

    private final static String REPOSITORY_UUID_PREFIX = "Repository-";

    private final PullRequestService pullRequestService;

    public RepositoryService(@NonNull final PullRequestService pullRequestService) {
        this.pullRequestService = Objects.requireNonNull(pullRequestService);
    }

    @NonNull
    @Override
    public List<RepositoryDTO> getAll() {
        return pullRequestService.getAll()
                .stream()
                .filter(pr -> pr.merged())
                .map(pr -> {
                    final String org = pr.org();
                    final String repository = pr.repository();
                    final String uuid = REPOSITORY_UUID_PREFIX + "/" + org + "/" + repository;
                    return new RepositoryDTO(uuid, org, repository);
                }).distinct().toList();
    }
}
