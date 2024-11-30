package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Department.DepartmentRequestDto;
import com.mt.jwtstarter.dto.Department.DepartmentResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Page<DepartmentResponseDto> getAllDepartments(int pageNumber, int pageSize);

    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentResponseDto);

    DepartmentResponseDto getDepartmentById(Long id);

    Map<String, String> addSubcategoryToDepartment(Long departmentId, Integer subcategoryId);

    Map<String, String> removeSubcategoryFromDepartment(Long departmentId, Integer subcategoryId);

    List<DepartmentResponseDto> getDepartmentsByUser();
}
