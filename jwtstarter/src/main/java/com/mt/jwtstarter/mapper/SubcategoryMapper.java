package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Category.CategorySimpleResponseDto;
import com.mt.jwtstarter.dto.Subcategory.SubcategoryResponseDto;
import com.mt.jwtstarter.model.Subcategory;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryMapper {

    public static SubcategoryResponseDto mapToSubcategoryResponseDto(Subcategory subcategory) {
        return SubcategoryResponseDto.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .category(CategorySimpleResponseDto.builder()
                        .id(subcategory.getCategory().getId())
                        .name(subcategory.getCategory().getName())
                        .build())
                .build();
    }
}
