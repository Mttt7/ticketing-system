package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Department.DepartmentRequestDto;
import com.mt.jwtstarter.dto.Department.DepartmentResponseDto;
import com.mt.jwtstarter.exception.EntityNotFound;
import com.mt.jwtstarter.mapper.DepartmentMapper;
import com.mt.jwtstarter.model.Department;
import com.mt.jwtstarter.model.Subcategory;
import com.mt.jwtstarter.repository.DepartmentRepository;
import com.mt.jwtstarter.repository.SubcategoryRepository;
import com.mt.jwtstarter.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public Page<DepartmentResponseDto> getAllDepartments(int pageNumber, int pageSize) {
        Page<Department> res = departmentRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PageImpl<>(
                res.getContent().stream().map(DepartmentMapper::mapToDepartmentResponseDto).toList(),
                PageRequest.of(pageNumber, pageSize),
                res.getTotalElements()
        );
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentResponseDto) {
        departmentRepository.findByName(departmentResponseDto.getName()).ifPresent(
                department -> {
                    throw new EntityNotFound("Department already exists");
                }
        );
        Department department = DepartmentMapper.mapToDepartment(departmentResponseDto);
        return DepartmentMapper.mapToDepartmentResponseDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        return DepartmentMapper.mapToDepartmentResponseDto(departmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFound("Department not found")
        ));
    }

    @Override
    public Map<String, String> addSubcategoryToDepartment(Long departmentId, Integer subcategoryId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new EntityNotFound("Department not found")
        );
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId).orElseThrow(
                () -> new EntityNotFound("Subcategory not found")
        );
        if (department.getSubcategories().contains(subcategory)) {
            return Map.of("message", "Subcategory already added to department");
        }
        department.getSubcategories().add(subcategory);
        departmentRepository.save(department);
        return Map.of("message", "Subcategory added to department");
    }

    @Override
    public Map<String, String> removeSubcategoryFromDepartment(Long departmentId, Integer subcategoryId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new EntityNotFound("Department not found")
        );
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId).orElseThrow(
                () -> new EntityNotFound("Subcategory not found")
        );
        if (department.getSubcategories().contains(subcategory)) {
            department.getSubcategories().remove(subcategory);
            departmentRepository.save(department);
            return Map.of("message", "Subcategory removed from department");
        } else {
            return Map.of("message", "Subcategory not found in department or already removed");
        }
    }
}
