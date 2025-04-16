package com.openelements.opendata.organization;

import com.openelements.opendata.base.HttpUtils;
import com.openelements.opendata.base.Language;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<OrganizationDTO>> getAll(@NonNull HttpServletRequest request) {
        final Language language = HttpUtils.getLanguageFromHeader(request);
        List<OrganizationDTO> result = service.getAll(language);
        return HttpUtils.buildResponse(result, language);
    }

    @GetMapping(value = "/organizations/count", produces = {"application/json"})
    @Operation(summary = "Endpoint to get the number of organizations")
    public long getCount() {
        return service.getCount();
    }

}
