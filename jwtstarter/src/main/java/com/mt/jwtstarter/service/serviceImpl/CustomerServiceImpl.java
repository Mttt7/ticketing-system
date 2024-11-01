package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Customer.CustomerRequestDto;
import com.mt.jwtstarter.dto.Customer.CustomerResponseDto;
import com.mt.jwtstarter.exception.CustomerNotFound;
import com.mt.jwtstarter.mapper.CustomerMapper;
import com.mt.jwtstarter.model.Customer;
import com.mt.jwtstarter.repository.CustomerRepository;
import com.mt.jwtstarter.service.CustomerService;
import com.mt.jwtstarter.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerRequestDto);
        return CustomerMapper.mapToCustomerResponseDto(customerRepository.save(customer));
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        return CustomerMapper.mapToCustomerResponseDto(customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFound("Customer not found")
        ));
    }

    @Override
    public Page<CustomerResponseDto> searchCustomers(String phone,
                                                     String email,
                                                     String firstName,
                                                     String lastName,
                                                     int pageNumber,
                                                     int pageSize) {
        Specification<Customer> spec = Specification.where(CustomerSpecification.hasFirstName(firstName))
                .and(CustomerSpecification.hasLastName(lastName))
                .and(CustomerSpecification.hasEmail(email))
                .and(CustomerSpecification.hasPhone(phone));

        return customerRepository.findAll(spec, PageRequest.of(pageNumber, pageSize))
                .map(CustomerMapper::mapToCustomerResponseDto);
    }

    @Override
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFound("Customer not found")
        );
        customer.setFirstName(customerRequestDto.getFirstName());
        customer.setLastName(customerRequestDto.getLastName());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setPhone(customerRequestDto.getPhone());
        return CustomerMapper.mapToCustomerResponseDto(customerRepository.save(customer));
    }
}
