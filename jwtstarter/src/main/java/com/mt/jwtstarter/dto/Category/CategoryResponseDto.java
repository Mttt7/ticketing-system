package com.mt.jwtstarter.dto.Category;

import com.mt.jwtstarter.dto.Subcategory.SubcategoryResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryResponseDto {
    private Integer id;
    private String name;
    private List<SubcategoryResponseDto> subcategories;
}
