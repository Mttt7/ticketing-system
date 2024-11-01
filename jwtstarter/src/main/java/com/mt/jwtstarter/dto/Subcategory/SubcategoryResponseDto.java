package com.mt.jwtstarter.dto.Subcategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubcategoryResponseDto {
    private Integer id;
    private String name;
}
