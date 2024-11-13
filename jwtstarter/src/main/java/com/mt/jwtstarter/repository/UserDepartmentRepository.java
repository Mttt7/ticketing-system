package com.mt.jwtstarter.repository;

import com.mt.jwtstarter.model.Department;
import com.mt.jwtstarter.model.UserDepartment;
import com.mt.jwtstarter.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Long> {

    Boolean existsByUserAndDepartment(UserEntity user, Department department);

    Page<UserDepartment> findAllByDepartment(Department department, PageRequest of);
}
