package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import com.mt.jwtstarter.exception.CategoryNotFound;
import com.mt.jwtstarter.mapper.TicketMapper;
import com.mt.jwtstarter.model.*;
import com.mt.jwtstarter.repository.CategoryRepository;
import com.mt.jwtstarter.repository.SubcategoryRepository;
import com.mt.jwtstarter.repository.TicketRepository;
import com.mt.jwtstarter.repository.UserTicketFollowerRepository;
import com.mt.jwtstarter.service.AuthService;
import com.mt.jwtstarter.service.TicketService;
import com.mt.jwtstarter.specification.TicketSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final AuthService authService;
    private final UserTicketFollowerRepository userTicketFollowerRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        UserEntity user = authService.getLoggedUser();
        Category category = categoryRepository.findById(ticketRequestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFound("Category not found")
        );
        Subcategory subcategory = subcategoryRepository.findById(ticketRequestDto.getSubcategoryId()).orElseThrow(
                () -> new CategoryNotFound("Subcategory not found")
        );
        if (!category.getSubcategories().contains(subcategory)) {
            throw new CategoryNotFound("Subcategory does not belong to the category");
        }

        Ticket ticket = ticketMapper.mapToTicket(ticketRequestDto);
        Ticket ticketDb = ticketRepository.save(ticket);

        UserTicketFollower userTicketFollower = new UserTicketFollower();
        userTicketFollower.setUser(user);
        userTicketFollower.setTicket(ticketDb);
        userTicketFollowerRepository.save(userTicketFollower);

        return ticketMapper.mapToTicketResponseDto(ticketDb);
    }

    @Override
    public Page<TicketResponseDto> getFollowedTickets(int userId, int pageNumber, int pageSize) {
        UserEntity user = authService.getLoggedUser();
        Page<UserTicketFollower> response = userTicketFollowerRepository.findAllByUser(user, PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                response.getContent().stream().map(userTicketFollower -> ticketMapper.mapToTicketResponseDto(userTicketFollower.getTicket())).collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                response.getTotalElements()
        );
    }

    @Override
    public Map<String, String> followTicket(Long ticketId) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        UserTicketFollower utf = userTicketFollowerRepository.findByUser(user);
        if (utf != null) {
            return Map.of("message", "Ticket already followed");
        } else {
            UserTicketFollower userTicketFollower = new UserTicketFollower();
            userTicketFollower.setUser(user);
            userTicketFollower.setTicket(ticket);
            userTicketFollowerRepository.save(userTicketFollower);
            return Map.of("message", "Ticket followed");
        }
    }

    @Override
    public Map<String, String> unfollowTicket(Long ticketId) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        UserTicketFollower utf = userTicketFollowerRepository.findByUser(user);
        if (utf != null) {
            userTicketFollowerRepository.delete(utf);
            return Map.of("message", "Ticket unfollowed");
        } else {
            return Map.of("message", "Ticket not followed");
        }
    }

    @Override
    public Page<TicketResponseDto> searchTickets(Long ticketId,
                                                 Long customerId,
                                                 String customerEmail,
                                                 String customerPhone,
                                                 String content,
                                                 Boolean isOpen,
                                                 Channel channel,
                                                 Long categoryId,
                                                 Long subcategoryId,
                                                 Priority priority,
                                                 Long openedById,
                                                 Long closedById,
                                                 LocalDate createdAfter,
                                                 LocalDate createdBefore,
                                                 int pageNumber,
                                                 int pageSize) {

        Timestamp createdAfterTimestamp = createdAfter != null ? Timestamp.valueOf(createdAfter.atStartOfDay()) : null;
        Timestamp createdBeforeTimestamp = createdBefore != null ? Timestamp.valueOf(createdBefore.atTime(23, 59, 59)) : null;

        Specification<Ticket> spec = Specification.where(TicketSpecification.hasCustomerId(customerId))
                .and(TicketSpecification.hasTicketId(ticketId))
                .and(TicketSpecification.hasCustomerEmail(customerEmail))
                .and(TicketSpecification.hasCustomerPhone(customerPhone))
                .and(TicketSpecification.hasContent(content))
                .and(TicketSpecification.isOpen(isOpen))
                .and(TicketSpecification.hasChannel(channel))
                .and(TicketSpecification.hasCategoryId(categoryId))
                .and(TicketSpecification.hasSubcategoryId(subcategoryId))
                .and(TicketSpecification.hasPriority(priority))
                .and(TicketSpecification.hasOpenedById(openedById))
                .and(TicketSpecification.hasClosedById(closedById))
                .and(TicketSpecification.createdAfter(createdAfterTimestamp))
                .and(TicketSpecification.createdBefore(createdBeforeTimestamp));

        Page<Ticket> response = ticketRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending()));
        return new PageImpl<>(response.getContent().stream()
                .map(ticketMapper::mapToTicketResponseDto)
                .collect(Collectors.toList()), PageRequest.of(pageNumber, pageSize), response.getTotalElements());
    }

    @Override
    public TicketResponseDto getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticketMapper.mapToTicketResponseDto(ticket);
    }
}
