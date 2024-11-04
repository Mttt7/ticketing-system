package com.mt.jwtstarter.service;

import com.mt.jwtstarter.model.Customer;

public interface CustomerLookupService {
    Customer getCustomerById(Long id);
}
