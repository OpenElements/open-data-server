package com.openelements.opendata.employees;

import com.openelements.opendata.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Employee extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private String gitHubUsername;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGitHubUsername() {
        return gitHubUsername;
    }

    public void setGitHubUsername(String gitHubUsername) {
        this.gitHubUsername = gitHubUsername;
    }
}
