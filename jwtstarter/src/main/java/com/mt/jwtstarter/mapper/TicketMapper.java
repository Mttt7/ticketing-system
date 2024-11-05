package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.exception.CategoryNotFound;
import com.mt.jwtstarter.exception.SubcategoryNotFound;
import com.mt.jwtstarter.model.*;
import com.mt.jwtstarter.repository.CategoryRepository;
import com.mt.jwtstarter.repository.SubcategoryRepository;
import com.mt.jwtstarter.service.AuthService;
import com.mt.jwtstarter.service.CustomerLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketMapper {

    private final CustomerLookupService customerLookupService;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final AuthService authService;

    public Ticket mapToTicket(TicketRequestDto ticketRequestDto) {
        UserEntity user = authService.getLoggedUser();
        Customer customer = customerLookupService.getCustomerById(ticketRequestDto.getCustomerId());
        Category category = categoryRepository.findById(ticketRequestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFound("Category not found")
        );
        Subcategory subcategory = subcategoryRepository.findById(ticketRequestDto.getSubcategoryId()).orElseThrow(
                () -> new SubcategoryNotFound("Subcategory not found")
        );

        return Ticket.builder()
                .customer(customer)
                .channel(ticketRequestDto.getChannel())
                .content(ticketRequestDto.getContent())
                .category(category)
                .subcategory(subcategory)
                .isOpen(true)
                .openedBy(user)
                .priority(ticketRequestDto.getPriority())
                .build();
    }

    public TicketResponseDto mapToTicketResponseDto(Ticket ticket) {
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .channel(ticket.getChannel())
                .content(ticket.getContent())
                .category(CategoryMapper.mapToCategoryResponseDto(ticket.getCategory()))
                .subcategory(SubcategoryMapper.mapToSubcategoryResponseDto(ticket.getSubcategory()))
                .isOpen(ticket.getIsOpen())
                .priority(ticket.getPriority())
                .openedBy(UserMapper.mapToUserResponseDto(ticket.getOpenedBy()))
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .closedAt(ticket.getClosedAt())
                .closedBy(ticket.getClosedBy() != null ? UserMapper.mapToUserResponseDto(ticket.getClosedBy()) : null)
                .customer(ticket.getCustomer())
                .build();
    }
}
