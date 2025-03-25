package com.openelements.opendata.employees;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Schema(title = "Employee", description = "An Open Elements employee")
public record EmployeeDTO(
        @NonNull @Schema(description = "UUID of the employee", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
        @NonNull @Schema(description = "Name of the employee", example = "Jon Dao", required = true) String name,
        @Nullable @Schema(description = "Username at GitHub", example = "octocat", required = false) String gitHubUsername
) implements DTO {

    public EmployeeDTO {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
    }

}
