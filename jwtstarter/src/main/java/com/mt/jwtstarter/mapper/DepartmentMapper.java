package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Department.DepartmentRequestDto;
import com.mt.jwtstarter.dto.Department.DepartmentResponseDto;
import com.mt.jwtstarter.model.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {

public static DepartmentResponseDto mapToDepartmentResponseDto(Department department){
        return DepartmentResponseDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    public static Department mapToDepartment(DepartmentRequestDto departmentRequestDto){
        return Department.builder()
                .name(departmentRequestDto.getName())
                .build();
    }
}
