package com.mt.jwtstarter.controller;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.dto.Department.DepartmentRequestDto;
import com.mt.jwtstarter.dto.Department.DepartmentResponseDto;
import com.mt.jwtstarter.service.DepartmentService;
import com.mt.jwtstarter.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final UserDepartmentService userDepartmentService;

    @GetMapping("/")
    private ResponseEntity<Page<DepartmentResponseDto>> getAllDepartments(@RequestParam int pageNumber,
                                                                          @RequestParam int pageSize) {
        return ResponseEntity.ok(departmentService.getAllDepartments(pageNumber, pageSize));
    }

    @PostMapping("/")
    private ResponseEntity<DepartmentResponseDto> createDepartment(@RequestBody DepartmentRequestDto departmentRequestDto) {
        return ResponseEntity.ok(departmentService.createDepartment(departmentRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Page<UserResponseDto>> getUsersByDepartment(@PathVariable Long id,
                                                                      @RequestParam int pageNumber,
                                                                      @RequestParam int pageSize) {
        return ResponseEntity.ok(userDepartmentService.getUsersByDepartment(id, pageNumber, pageSize));
    }

    @PostMapping("/assign/{userId}/{departmentId}")
    public ResponseEntity<Map<String, String>> assignUserToDepartment(@PathVariable Long userId,
                                                                      @PathVariable Long departmentId) {

        return ResponseEntity.ok(userDepartmentService.assignUserToDepartment(userId, departmentId));
    }

    @PostMapping("/add-subcategory/{departmentId}/{subcategoryId}")
    public ResponseEntity<Map<String, String>> addSubcategoryToDepartment(@PathVariable Long departmentId,
                                                                          @PathVariable Integer subcategoryId) {

        return ResponseEntity.ok(departmentService.addSubcategoryToDepartment(departmentId, subcategoryId));
    }

    @DeleteMapping("/remove-subcategory/{departmentId}/{subcategoryId}")
    public ResponseEntity<Map<String, String>> removeSubcategoryFromDepartment(@PathVariable Long departmentId,
                                                                               @PathVariable Integer subcategoryId) {

        return ResponseEntity.ok(departmentService.removeSubcategoryFromDepartment(departmentId, subcategoryId));
    }
}
