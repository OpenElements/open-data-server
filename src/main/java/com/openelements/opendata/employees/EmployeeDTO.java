package com.openelements.opendata.employees;

import com.openelements.opendata.base.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import java.util.Objects;

@Schema(title = "Employee", description = "An Open Elements employee")
public record EmployeeDTO(@Schema(description = "UUID of the employee", example = "b14763b0-60e0-4861-ba30-53f17cc4471e", required = true) String uuid,
                          @Schema(description = "Name of the employee", example = "Jon Dao", required = true) String name,
                          @Schema(description = "Creation time of the data", example = "2025-03-23T21:03:13.123116+01:00", required = true) ZonedDateTime createdAt) implements DTO {

    public EmployeeDTO {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(createdAt, "createdAt cannot be null");
    }

}
