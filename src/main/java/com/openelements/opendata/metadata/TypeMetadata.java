package com.openelements.opendata.metadata;

import com.openelements.opendata.base.db.AbstractEntity;
import com.openelements.opendata.base.db.I18nString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TypeMetadata extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToOne(cascade = {CascadeType.ALL})
    private I18nString readableName;

    @OneToOne(cascade = {CascadeType.ALL})
    private I18nString description;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<AttributeMetadata> attributes = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public I18nString getReadableName() {
        return readableName;
    }

    public void setReadableName(I18nString readableName) {
        this.readableName = readableName;
    }

    public I18nString getDescription() {
        return description;
    }

    public void setDescription(I18nString description) {
        this.description = description;
    }

    public Set<AttributeMetadata> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeMetadata> attributes) {
        this.attributes = attributes;
    }
}
