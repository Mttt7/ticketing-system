package com.mt.jwtstarter.dto.Customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
