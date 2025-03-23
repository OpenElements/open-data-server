package com.openelements.opendata.base;

import java.time.ZonedDateTime;
import org.jspecify.annotations.NonNull;

public interface DTO {

    @NonNull
    String uuid();

    @NonNull
    ZonedDateTime createdAt();
}
