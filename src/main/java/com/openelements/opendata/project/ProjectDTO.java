package com.openelements.opendata.project;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(title = "Project", description = "An open source project")
public record ProjectDTO(
        @Schema(description = "UUID of the project", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
        @Schema(description = "Name of the project", example = "Spring Boot", required = true) String name,
        @Schema(description = "Description of the project", example = "A HTTP library to connect tio GitHub", required = true) String description,
        @Schema(description = "Path to the logo of the project", example = "TODO", required = true) String logoPath,
        @Schema(description = "Set of repository names that match to the project", example = "TODO", required = true) Set<String> matchingRepos) implements
        DTO {
}
