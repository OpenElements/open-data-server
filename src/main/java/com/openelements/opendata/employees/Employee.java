package com.openelements.opendata.employees;

import com.openelements.opendata.base.db.AbstractEntity;
import com.openelements.opendata.base.db.I18nString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Entity
public class Employee extends AbstractEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToOne(cascade = {CascadeType.ALL})
    private I18nString role;

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

    public I18nString getRole() {
        return role;
    }

    public void setRole(I18nString role) {
        this.role = role;
    }

    public String getGitHubUsername() {
        return gitHubUsername;
    }

    public void setGitHubUsername(@Nullable final String gitHubUsername) {
        this.gitHubUsername = gitHubUsername;
    }
}
