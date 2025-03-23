package com.openelements.opendata.employees;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.jspecify.annotations.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmployeesScheduler {

    private final EmployeeService employeeService;

    private final EmployeeProviderService employeeProviderService;

    public EmployeesScheduler(@NonNull final EmployeeService employeeService, @NonNull EmployeeProviderService employeeProviderService) {
        this.employeeService = Objects.requireNonNull(employeeService);
        this.employeeProviderService = Objects.requireNonNull(employeeProviderService);
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    private void updateEmployees() {
        employeeService.updateDatabase(employeeProviderService.get());
    }
}
