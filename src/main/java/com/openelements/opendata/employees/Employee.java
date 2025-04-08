package com.openelements.opendata.employees;

import com.openelements.opendata.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Entity
public class Employee extends AbstractEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String role;

    @Column
    private String gitHubUsername;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final @NonNull String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final @NonNull String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final @Nullable String role) {
        this.role = role;
    }

    public String getGitHubUsername() {
        return gitHubUsername;
    }

    public void setGitHubUsername(@Nullable final String gitHubUsername) {
        this.gitHubUsername = gitHubUsername;
    }
}
