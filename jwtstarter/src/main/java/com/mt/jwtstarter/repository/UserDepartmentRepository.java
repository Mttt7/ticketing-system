package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Department;
import com.mt.jwtstarter.model.UserDepartment;
import com.mt.jwtstarter.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Long> {

    Boolean existsByUserAndDepartment(UserEntity user, Department department);

    Page<UserDepartment> findAllByDepartment(Department department, PageRequest pg);

    Optional<UserDepartment> findByUserAndDepartment(UserEntity user, Department department);

    List<UserDepartment> findAllByUser(UserEntity user);
}
