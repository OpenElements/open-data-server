package com.openelements.opendata.employees;

import com.openelements.opendata.base.AbstractService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractService<EmployeeDTO, Employee> {

    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeService() {
        super(EmployeeMapper.class, Employee.class);
    }

    @Override
    protected @NonNull EntityManager getEntityManager() {
        return entityManager;
    }
}
