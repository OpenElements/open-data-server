package com.openelements.opendata.employees;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Schema(description = "Endpoint to get information about all employees")
public class EmployeeEndpoint {

    private final EmployeeService employeeService;

    public EmployeeEndpoint(@NonNull final EmployeeService employeeService) {
        this.employeeService = Objects.requireNonNull(employeeService);
    }

    @GetMapping(value = "/employees", produces = {"application/json"})
    @Operation(summary = "Endpoint to get information about all employees")
    public List<EmployeeDTO> getEmployees() {
        return employeeService.getAll();
    }

}
