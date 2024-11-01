package com.mt.jwtstarter.dto.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDto {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(min = 8, max = 13, message = "Phone number must be between 8 and 13 digits")
    @Pattern(regexp = "^\\d+$", message = "Phone number must contain only digits")
    private String phone;
}
