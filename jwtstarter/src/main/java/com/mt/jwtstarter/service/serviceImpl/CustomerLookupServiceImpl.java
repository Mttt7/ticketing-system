package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.exception.EntityNotFound;
import com.mt.jwtstarter.model.Customer;
import com.mt.jwtstarter.repository.CustomerRepository;
import com.mt.jwtstarter.service.CustomerLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerLookupServiceImpl implements CustomerLookupService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFound("Customer not found")
        );
    }
}
