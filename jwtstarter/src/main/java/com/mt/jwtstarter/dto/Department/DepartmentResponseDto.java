package com.mt.jwtstarter.dto.Department;

import com.mt.jwtstarter.dto.Subcategory.SubcategoryResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DepartmentResponseDto {
    private Long id;
    private String name;
    private List<SubcategoryResponseDto> subcategories;
}