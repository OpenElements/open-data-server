package com.openelements.opendata.pullrequests;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.jspecify.annotations.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PullRequestScheduler {

    private final PullRequestService pullRequestService;

    private final PullRequestProviderService pullRequestProviderService;

    public PullRequestScheduler(@NonNull final PullRequestService pullRequestService,
            @NonNull PullRequestProviderService pullRequestProviderService) {
        this.pullRequestService = Objects.requireNonNull(pullRequestService);
        this.pullRequestProviderService = Objects.requireNonNull(pullRequestProviderService);
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    private void updateEmployees() {
        pullRequestService.updateDatabase(pullRequestProviderService.get());
    }
}
