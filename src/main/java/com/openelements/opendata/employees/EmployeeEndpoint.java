package com.openelements.opendata.employees;

import com.openelements.opendata.base.HttpUtils;
import com.openelements.opendata.base.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "EmployeesEndpoint", description = "Endpoint to get information about all employees")
public class EmployeeEndpoint {

    private final EmployeeService employeeService;

    public EmployeeEndpoint(@NonNull final EmployeeService employeeService) {
        this.employeeService = Objects.requireNonNull(employeeService);
    }

    @NonNull
    @GetMapping(value = "/employees", produces = {"application/json"})
    @Operation(summary = "Endpoint to get information about all employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@NonNull HttpServletRequest request) {
        final Language acceptLanguage = HttpUtils.getLanguageFromHeader(request);
        List<EmployeeDTO> values = employeeService.getAll(acceptLanguage);
        return HttpUtils.buildResponse(values, acceptLanguage);
    }

    @GetMapping(value = "/employees/count", produces = {"application/json"})
    @Operation(summary = "Endpoint to get the number of employees")
    public long getCount() {
        return employeeService.getCount();
    }

}
