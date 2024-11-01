package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Subcategory.SubcategoryResponseDto;
import com.mt.jwtstarter.model.Subcategory;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryMapper {

    public static SubcategoryResponseDto mapToSubcategoryResponseDto(Subcategory subcategory) {
        return SubcategoryResponseDto.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .build();
    }
}
