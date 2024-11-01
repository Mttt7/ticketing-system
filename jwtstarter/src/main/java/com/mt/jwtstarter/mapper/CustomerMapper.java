package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Customer.CustomerRequestDto;
import com.mt.jwtstarter.dto.Customer.CustomerResponseDto;
import com.mt.jwtstarter.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public static CustomerResponseDto mapToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
    }

    public static Customer mapToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .firstName(customerRequestDto.getFirstName())
                .lastName(customerRequestDto.getLastName())
                .email(customerRequestDto.getEmail())
                .phone(customerRequestDto.getPhone())
                .build();
    }
}
