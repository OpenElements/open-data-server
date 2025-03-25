package com.openelements.opendata.repositories;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Repository", description = "A GitHub repository")
public record RepositoryDTO(
        @Schema(description = "UUID of the repository", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
        @Schema(description = "Name of the GitHub org", example = "OpenElements", required = true) String org,
        @Schema(description = "Name of the GitHub repository", example = "open-elements-website", required = true) String repository) implements
        DTO {
}
