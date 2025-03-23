package com.openelements.opendata.employees;

import com.openelements.opendata.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Employee extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
