package com.mt.jwtstarter.controller;

import com.mt.jwtstarter.dto.Customer.CustomerRequestDto;
import com.mt.jwtstarter.dto.Customer.CustomerResponseDto;
import com.mt.jwtstarter.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto){
        return ResponseEntity.ok(customerService.createCustomer(customerRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerDtoById(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequestDto customerRequestDto){
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequestDto));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CustomerResponseDto>> searchCustomer(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<CustomerResponseDto> customers = customerService.searchCustomers(phone, email, firstName, lastName, pageNumber, pageSize);
        return ResponseEntity.ok(customers);
    }
}
