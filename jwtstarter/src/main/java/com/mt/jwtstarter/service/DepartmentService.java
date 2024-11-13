package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Department.DepartmentRequestDto;
import com.mt.jwtstarter.dto.Department.DepartmentResponseDto;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Page<DepartmentResponseDto> getAllDepartments(int pageNumber, int pageSize);

    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentResponseDto);

    DepartmentResponseDto getDepartmentById(Long id);
}
