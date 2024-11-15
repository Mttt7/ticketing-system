package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Category.CategoryResponseDto;
import com.mt.jwtstarter.mapper.SubcategoryMapper;
import com.mt.jwtstarter.repository.CategoryRepository;
import com.mt.jwtstarter.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryResponseDto> findAllCategories() {
        return categoryRepository.findAll().stream().map(category -> CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .subcategories(category.getSubcategories().stream().map(SubcategoryMapper::mapToSubcategoryResponseDto).toList())
                .build()).toList();
    }
}
