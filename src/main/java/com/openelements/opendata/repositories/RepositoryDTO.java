package com.openelements.opendata.repositories;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

@Schema(title = "Repository", description = "A GitHub repository")
public record RepositoryDTO(
        @NonNull @Schema(description = "UUID of the repository", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
        @NonNull @Schema(description = "Name of the GitHub org", example = "OpenElements", required = true) String org,
        @NonNull @Schema(description = "Name of the GitHub repository", example = "open-elements-website", required = true) String repository) implements
        DTO {

    public RepositoryDTO {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        Objects.requireNonNull(org, "org cannot be null");
        Objects.requireNonNull(repository, "repository cannot be null");
    }
}
