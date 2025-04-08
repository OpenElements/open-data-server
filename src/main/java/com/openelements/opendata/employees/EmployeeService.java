package com.openelements.opendata.employees;

import com.openelements.opendata.base.AbstractEntityBasedService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractEntityBasedService<EmployeeDTO, Employee> {

    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeService() {
        super(EmployeeMapper.class, Employee.class);
    }

    @NonNull
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
