package com.openelements.opendata.repositories;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "RepositoriesEndpoint", description = "Endpoint to get information about all repositories Open Elements employees have done contributions to")
public class RepositoryEndpoint {

    private final RepositoryService repositoryService;

    public RepositoryEndpoint(@NonNull final RepositoryService repositoryService) {
        this.repositoryService = Objects.requireNonNull(repositoryService);
    }

    @NonNull
    @GetMapping(value = "/repositories", produces = {"application/json"})
    @Operation(summary = "Endpoint to get information about all repositories")
    public List<RepositoryDTO> getPullRequests() {
        return repositoryService.getAll();
    }

    @GetMapping(value = "/repositories/count", produces = {"application/json"})
    @Operation(summary = "Endpoint to get the number of repositories")
    public int getCount() {
        return repositoryService.getAll().size();
    }

}
