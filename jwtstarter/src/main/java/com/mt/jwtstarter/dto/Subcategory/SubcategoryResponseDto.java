package com.mt.jwtstarter.dto.Subcategory;

import com.mt.jwtstarter.dto.Category.CategorySimpleResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubcategoryResponseDto {
    private Integer id;
    private String name;
    private CategorySimpleResponseDto category;
}
