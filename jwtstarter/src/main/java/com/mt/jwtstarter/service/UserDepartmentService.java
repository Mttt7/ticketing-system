package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UserDepartmentService {

    Map<String, String> assignUserToDepartment(Long userId, Long departmentId);

    Page<UserResponseDto> getUsersByDepartment(Long id, int pageNumber, int pageSize);

    Map<String, String> removeUserFromDepartment(Long userId, Long departmentId);
}
