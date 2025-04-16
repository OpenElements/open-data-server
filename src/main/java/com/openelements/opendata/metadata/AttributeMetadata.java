package com.openelements.opendata.metadata;

import com.openelements.opendata.base.db.AbstractEntity;
import com.openelements.opendata.base.db.I18nString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class AttributeMetadata extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = {CascadeType.ALL})
    private I18nString readableName;

    @OneToOne(cascade = {CascadeType.ALL})
    private I18nString description;

    @Column(nullable = false)
    private DataType dataType;

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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
