package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Category.CategoryResponseDto;
import com.mt.jwtstarter.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public static CategoryResponseDto mapToCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
