package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Department.DepartmentRequestDto;
import com.mt.jwtstarter.dto.Department.DepartmentResponseDto;
import com.mt.jwtstarter.dto.Subcategory.SubcategoryResponseDto;
import com.mt.jwtstarter.model.Department;
import com.mt.jwtstarter.model.Subcategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentMapper {

    public static DepartmentResponseDto mapToDepartmentResponseDto(Department department) {
        List<Subcategory> subcategories = department.getSubcategories();
        if(subcategories == null) {
            return DepartmentResponseDto.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .build();
        }
        List<SubcategoryResponseDto> subcategoryDtos = subcategories.stream().map(SubcategoryMapper::mapToSubcategoryResponseDto).toList();
        return DepartmentResponseDto.builder()
                .id(department.getId())
                .name(department.getName())
                .subcategories(subcategoryDtos)
                .build();
    }

    public static Department mapToDepartment(DepartmentRequestDto departmentRequestDto) {
        return Department.builder()
                .name(departmentRequestDto.getName())
                .build();
    }
}
