package com.openelements.opendata.base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.ZonedDateTime;

@Entity
public class UpdateEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String type;

    @Column(nullable = false)
    private ZonedDateTime lastUpdate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
