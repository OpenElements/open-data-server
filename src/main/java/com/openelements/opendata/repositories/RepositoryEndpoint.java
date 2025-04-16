package com.openelements.opendata.repositories;

import com.openelements.opendata.base.HttpUtils;
import com.openelements.opendata.base.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<RepositoryDTO>> getPullRequests(@NonNull HttpServletRequest request) {
        final Language language = HttpUtils.getLanguageFromHeader(request);
        List<RepositoryDTO> result = repositoryService.getAll(language);
        return HttpUtils.buildResponse(result, language);
    }

    @GetMapping(value = "/repositories/count", produces = {"application/json"})
    @Operation(summary = "Endpoint to get the number of repositories")
    public long getCount() {
        return repositoryService.getCount();
    }

}
