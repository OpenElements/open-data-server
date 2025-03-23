package com.openelements.opendata.employees;

import com.openelements.opendata.base.DtoMapper;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper extends DtoMapper<EmployeeDTO, Employee> {
}