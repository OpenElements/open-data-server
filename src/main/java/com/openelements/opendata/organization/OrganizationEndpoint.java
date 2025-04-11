package com.openelements.opendata.organization;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationEndpoint {

    private final OrganizationService service;

    public OrganizationEndpoint(@NonNull final OrganizationService service) {
        this.service = Objects.requireNonNull(service);
    }

    @NonNull
    @GetMapping(value = "/organizations", produces = {"application/json"})
    @Operation(summary = "Endpoint to get all organizations info for Open Elements")
    public List<OrganizationDTO> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/organizations/count", produces = {"application/json"})
    @Operation(summary = "Endpoint to get the number of organizations")
    public int getCount() {
        return service.getAll().size();
    }

}
