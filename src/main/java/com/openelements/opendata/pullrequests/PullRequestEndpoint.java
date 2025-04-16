package com.openelements.opendata.pullrequests;

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
@Tag(name = "PullRequestsEndpoint", description = "Endpoint to get information about all pull requests done by Open Elements employees")
public class PullRequestEndpoint {

    private final PullRequestService pullRequestService;

    public PullRequestEndpoint(@NonNull final PullRequestService pullRequestService) {
        this.pullRequestService = Objects.requireNonNull(pullRequestService);
    }

    @NonNull
    @GetMapping(value = "/pullrequests", produces = {"application/json"})
    @Operation(summary = "Endpoint to get information about all pull requests")
    public ResponseEntity<List<PullRequestDTO>> getPullRequests(@NonNull HttpServletRequest request) {
        final Language language = HttpUtils.getLanguageFromHeader(request);
        List<PullRequestDTO> result = pullRequestService.getAll(language);
        return HttpUtils.buildResponse(result, language);
    }

    @GetMapping(value = "/pullrequests/count", produces = {"application/json"})
    @Operation(summary = "Endpoint to get the number of pull requests")
    public long getCount() {
        return pullRequestService.getCount();
    }

}
