package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Department.DepartmentRequestDto;
import com.mt.jwtstarter.dto.Department.DepartmentResponseDto;
import com.mt.jwtstarter.mapper.DepartmentMapper;
import com.mt.jwtstarter.model.Department;
import com.mt.jwtstarter.repository.DepartmentRepository;
import com.mt.jwtstarter.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
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
      Department department =  DepartmentMapper.mapToDepartment(departmentResponseDto);
      return DepartmentMapper.mapToDepartmentResponseDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        return DepartmentMapper.mapToDepartmentResponseDto(departmentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Department not found")
        ));
    }
}
