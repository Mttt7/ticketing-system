package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Customer.CustomerRequestDto;
import com.mt.jwtstarter.dto.Customer.CustomerResponseDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.exception.EntityNotFound;
import com.mt.jwtstarter.mapper.CustomerMapper;
import com.mt.jwtstarter.mapper.TicketMapper;
import com.mt.jwtstarter.model.Customer;
import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.repository.CustomerRepository;
import com.mt.jwtstarter.repository.TicketRepository;
import com.mt.jwtstarter.service.CustomerService;
import com.mt.jwtstarter.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper tickerMapper;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerRequestDto);
        return CustomerMapper.mapToCustomerResponseDto(customerRepository.save(customer));
    }

    @Override
    public CustomerResponseDto getCustomerDtoById(Long id) {
        return CustomerMapper.mapToCustomerResponseDto(customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFound("Customer not found")
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

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("id"));

        return customerRepository.findAll(spec, pageRequest)
                .map(CustomerMapper::mapToCustomerResponseDto);
    }

    @Override
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFound("Customer not found")
        );
        customer.setFirstName(customerRequestDto.getFirstName());
        customer.setLastName(customerRequestDto.getLastName());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setPhone(customerRequestDto.getPhone());
        return CustomerMapper.mapToCustomerResponseDto(customerRepository.save(customer));
    }

    @Override
    public Page<TicketResponseDto> getCustomerTickets(Long id, int pageNumber, int pageSize) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFound("Customer not found")
        );
        Page<Ticket> tickets = ticketRepository.findAllByCustomerId(id, PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending()));

        return new PageImpl<>(
                tickets.getContent().stream().map(tickerMapper::mapToTicketResponseDto).collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                tickets.getTotalElements()
        );
    }
}
