package com.openelements.opendata.project;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import java.util.Set;
import org.jspecify.annotations.NonNull;

@Schema(title = "Project", description = "An open source project")
public record ProjectDTO(
        @NonNull @Schema(description = "UUID of the project", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
        @NonNull @Schema(description = "Name of the project", example = "Spring Boot", required = true) String name,
        @NonNull @Schema(description = "Description of the project", example = "A HTTP library to connect tio GitHub", required = true) String description,
        @NonNull @Schema(description = "Path to the logo of the project", example = "TODO", required = true) String logoPath,
        @NonNull @Schema(description = "Set of repository names that match to the project", example = "TODO", required = true) Set<String> matchingRepos) implements
        DTO {

    public ProjectDTO {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(description, "description cannot be null");
        Objects.requireNonNull(logoPath, "logoPath cannot be null");
        Objects.requireNonNull(matchingRepos, "matchingRepos cannot be null");
    }
}
