package com.openelements.opendata.pullrequests;

import com.openelements.opendata.base.AbstractProviderService;
import com.openelements.opendata.base.UpdateEntityRepository;
import com.openelements.opendata.employees.EmployeeDTO;
import com.openelements.opendata.employees.EmployeeService;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedSearchIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PullRequestProviderService extends AbstractProviderService<PullRequestDTO> {

    private final static Logger log = LoggerFactory.getLogger(PullRequestProviderService.class);

    private final static String EMPLOYEE_UUID_PREFIX = "PullRequest-";

    private final static ZonedDateTime MIN_TIME = ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    private final EmployeeService employeeService;

    @Value("${github.token}")
    private String gitHubToken;

    public PullRequestProviderService(@NonNull final UpdateEntityRepository updateRepository,
            @NonNull final EmployeeService employeeService) {
        super(PullRequestDTO.class, updateRepository);
        this.employeeService = Objects.requireNonNull(employeeService);
    }

    @NonNull
    @Override
    public List<PullRequestDTO> getAvailableData(@NonNull ZonedDateTime lastUpdate) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.gitHubUsername() != null)
                .map(EmployeeDTO::gitHubUsername)
                .flatMap(username -> getAvailablePullRequestsForAuthor(username, lastUpdate).stream())
                .toList();
    }

    @NonNull
    public List<PullRequestDTO> getAvailablePullRequestsForAuthor(@NonNull final String author,
            @NonNull final ZonedDateTime lastUpdate) {
        Objects.requireNonNull(author, "author cannot be null");
        Objects.requireNonNull(lastUpdate, "lastUpdate cannot be null");
        final List<PullRequestDTO> result = new ArrayList<>();
        try {
            log.info("Processing pull requests for author: {}", author);
            final GitHub github = new GitHubBuilder().withOAuthToken(gitHubToken).build();
            final ZonedDateTime updatedAfter;
            if (lastUpdate.isBefore(MIN_TIME)) {
                updatedAfter = MIN_TIME;
            } else {
                updatedAfter = lastUpdate;
            }
            final GHUser user = github.getUser(author);
            final PagedSearchIterable<GHPullRequest> list = github.searchPullRequests()
                    .author(user)
                    .updatedAfter(updatedAfter.toLocalDate(), false)
                    .list().withPageSize(100);
            for (GHPullRequest pullRequest : list) {
                final String org = pullRequest.getRepository().getOwnerName();
                final String repo = pullRequest.getRepository().getName();
                final long gitHubId = pullRequest.getNumber();
                final String uuid = EMPLOYEE_UUID_PREFIX + org + "/" + repo + "/" + gitHubId;
                final ZonedDateTime createdAt = ZonedDateTime.ofInstant(pullRequest.getCreatedAt().toInstant(),
                        ZoneOffset.UTC);
                final ZonedDateTime lastUpdateInGitHub = Optional.of(pullRequest.getUpdatedAt())
                        .map(date -> ZonedDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC))
                        .orElse(createdAt);
                final String title = pullRequest.getTitle();
                final boolean open = pullRequest.getState().equals(GHIssueState.OPEN);
                final boolean merged = pullRequest.isMerged();
                final boolean draft = pullRequest.isDraft();

                log.info("Processing pull request: {}", uuid);

                final PullRequestDTO pr = new PullRequestDTO(
                        uuid,
                        org,
                        repo,
                        gitHubId,
                        title,
                        createdAt,
                        lastUpdateInGitHub,
                        open,
                        draft,
                        merged,
                        author
                );
                result.add(pr);
            }
        } catch (final Exception e) {
            throw new RuntimeException("Error", e);
        }
        return result;
    }

    @Override
    protected @NonNull List<String> getUUIDBlacklist() {
        return List.of(EMPLOYEE_UUID_PREFIX + "support-and-care" + "/" + "jira-to-gh-issue-dummy-repo" + "/" + "1");
    }
}
