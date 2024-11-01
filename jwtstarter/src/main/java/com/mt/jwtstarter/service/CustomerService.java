package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Customer.CustomerRequestDto;
import com.mt.jwtstarter.dto.Customer.CustomerResponseDto;
import com.mt.jwtstarter.model.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);

    CustomerResponseDto getCustomerDtoById(Long id);

    Customer getCustomerById(Long id);

    Page<CustomerResponseDto> searchCustomers(String phone, String email, String firstName, String lastName, int pageNumber, int pageSize);

    CustomerResponseDto updateCustomer(Long id, CustomerRequestDto customerRequestDto);
}
