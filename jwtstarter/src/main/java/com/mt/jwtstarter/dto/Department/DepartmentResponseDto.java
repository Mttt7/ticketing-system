package com.mt.jwtstarter.dto.Department;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponseDto {
    private Long id;
    private String name;
}
