package com.openelements.opendata.base;

import java.util.List;
import org.jspecify.annotations.NonNull;

public abstract class AbstractService<T extends DTO> {

    @NonNull
    public abstract List<T> getAll();

}
