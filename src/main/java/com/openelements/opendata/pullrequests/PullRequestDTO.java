package com.openelements.opendata.pullrequests;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;

@Schema(title = "PullRequest", description = "A GitHub pull request")
public record PullRequestDTO(
        @Schema(description = "UUID of the employee", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
        @Schema(description = "Name of the GitHub org", example = "OpenElements", required = true) String org,
        @Schema(description = "Name of the GitHub repository", example = "open-elements-website", required = true) String repository,
        @Schema(description = "ID of the pull request in GitHub", example = "2937652873", required = true) long gitHubId,
        @Schema(description = "Title of the pull request", example = "Add a great new feature", required = true) String title,
        @Schema(description = "Creation time of the pull request in GitHub", example = "2025-03-23T21:03:13.123116+01:00", required = true) ZonedDateTime createdAtInGitHub,
        @Schema(description = "If pull request is open", required = true) boolean open,
        @Schema(description = "If pull request is merged", required = true) boolean merged,
        @Schema(description = "GitHub name of the author of the pull request", example = "octocat", required = true) String author) implements
        DTO {
}
