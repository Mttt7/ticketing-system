package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.exception.EntityNotFound;
import com.mt.jwtstarter.mapper.UserMapper;
import com.mt.jwtstarter.model.Department;
import com.mt.jwtstarter.model.UserDepartment;
import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.repository.DepartmentRepository;
import com.mt.jwtstarter.repository.UserDepartmentRepository;
import com.mt.jwtstarter.repository.UserRepository;
import com.mt.jwtstarter.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserDepartmentServiceImpl implements UserDepartmentService {
    private final UserDepartmentRepository userDepartmentRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Map<String, String> assignUserToDepartment(Long userId, Long departmentId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFound("User not found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new EntityNotFound("Department not found"));

        if (userDepartmentRepository.existsByUserAndDepartment(user, department)) {
            return Map.of("message", "User already assigned to department");
        }
        UserDepartment ud = new UserDepartment();
        ud.setUser(user);
        ud.setDepartment(department);
        userDepartmentRepository.save(ud);
        return Map.of("message", "User assigned to department successfully");
    }

    @Override
    public Page<UserResponseDto> getUsersByDepartment(Long id, int pageNumber, int pageSize) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFound("Department not found"));
        Page<UserDepartment> userDepartments = userDepartmentRepository.findAllByDepartment(department, PageRequest.of(pageNumber, pageSize));
        return new PageImpl<>(
                userDepartments.getContent().stream().map(UserDepartment::getUser).map(UserMapper::mapToUserResponseDto).toList(),
                PageRequest.of(pageNumber, pageSize),
                userDepartments.getTotalElements()
        );
    }
}
