package com.openelements.opendata.pullrequests;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

@Schema(title = "PullRequest", description = "A GitHub pull request")
public record PullRequestDTO(
        @NonNull @Schema(description = "UUID of the employee", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
        @NonNull @Schema(description = "Name of the GitHub org", example = "OpenElements", required = true) String org,
        @NonNull @Schema(description = "Name of the GitHub repository", example = "open-elements-website", required = true) String repository,
        @NonNull @Schema(description = "ID of the pull request in GitHub", example = "2937652873", required = true) long gitHubId,
        @NonNull @Schema(description = "Title of the pull request", example = "Add a great new feature", required = true) String title,
        @NonNull @Schema(description = "Creation time of the pull request in GitHub", example = "2025-03-23T21:03:13.123116+01:00", required = true) ZonedDateTime createdAtInGitHub,
        @NonNull @Schema(description = "Creation time of the pull request in GitHub", example = "2025-03-23T21:03:13.123116+01:00", required = true) ZonedDateTime lastUpdateInGitHub,
        @Schema(description = "If pull request is open", required = true) boolean open,
        @Schema(description = "If pull request is in draft mode", required = true) boolean draft,
        @Schema(description = "If pull request is merged", required = true) boolean merged,
        @NonNull @Schema(description = "GitHub name of the author of the pull request", example = "octocat", required = true) String author) implements
        DTO {

    public PullRequestDTO {
        Objects.requireNonNull("uuid", "uuid cannot be null");
        Objects.requireNonNull("org", "org cannot be null");
        Objects.requireNonNull("repository", "repository cannot be null");
        Objects.requireNonNull("gitHubId", "gitHubId cannot be null");
        Objects.requireNonNull("title", "title cannot be null");
        Objects.requireNonNull("createdAtInGitHub", "createdAtInGitHub cannot be null");
        Objects.requireNonNull("lastUpdateInGitHub", "lastUpdateInGitHub cannot be null");
        Objects.requireNonNull("author", "author cannot be null");
    }
}
