package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> findAllCategories();
}
