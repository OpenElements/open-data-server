package com.openelements.opendata.data;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NonNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "DataEndpoint", description = "Endpoint to get raw data like image")
public class DataEndpoint {


    @NonNull
    @GetMapping(value = "/data/{type}/{name}")
    @Operation(summary = "Endpoint to get raw data")
    public ResponseEntity<Resource> getData(@Parameter(
            description = "Data type (png, svg)",
            required = true,
            in = ParameterIn.PATH
    ) @PathVariable(name = "type") final DataType type, @Parameter(
            description = "File name to fetch (e.g. logo.svg)",
            required = true,
            in = ParameterIn.PATH
    ) @PathVariable(name = "name") final String name) {
        final InputStreamResource resource = new InputStreamResource(
                DataEndpoint.class.getClassLoader().getResourceAsStream("data/" + name));

        return ResponseEntity.ok()
                .contentType(type.getMediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + name + "\"")
                .body(resource);
    }
}
