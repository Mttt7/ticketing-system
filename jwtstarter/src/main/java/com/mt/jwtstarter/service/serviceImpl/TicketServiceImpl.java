package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.dto.Ticket.SearchTicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.StatsResponseDto;
import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.exception.EntityNotFound;
import com.mt.jwtstarter.mapper.TicketMapper;
import com.mt.jwtstarter.mapper.UserMapper;
import com.mt.jwtstarter.model.*;
import com.mt.jwtstarter.repository.*;
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
import java.util.List;
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
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        UserEntity user = authService.getLoggedUser();
        Category category = categoryRepository.findById(ticketRequestDto.getCategoryId()).orElseThrow(
                () -> new EntityNotFound("Category not found")
        );
        Subcategory subcategory = subcategoryRepository.findById(ticketRequestDto.getSubcategoryId()).orElseThrow(
                () -> new EntityNotFound("Subcategory not found")
        );
        if (!category.getSubcategories().contains(subcategory)) {
            throw new EntityNotFound("Subcategory does not belong to the category");
        }

        Ticket ticket = ticketMapper.mapToTicket(ticketRequestDto);
        return ticketMapper.mapToTicketResponseDto(ticketRepository.save(ticket));
    }

    @Override
    public Page<TicketResponseDto> getFollowedTicketsByUserId(Long userId, int pageNumber, int pageSize, String sort) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Sort sortType;
        if (sort.equals("dateDesc")) {
            sortType = Sort.by("createdAt").descending();
        } else {
            sortType = Sort.by("createdAt").ascending();
        }
        Page<UserTicketFollower> response = userTicketFollowerRepository.findAllByUserAndTicket_IsOpen(user, Boolean.TRUE, PageRequest.of(pageNumber, pageSize, sortType));

        return new PageImpl<>(
                response.getContent().stream().map(userTicketFollower -> ticketMapper.mapToTicketResponseDto(userTicketFollower.getTicket())).collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                response.getTotalElements()
        );
    }

    @Override
    public Map<String, Boolean> isTicketFollowed(Long ticketId) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        UserTicketFollower utf = userTicketFollowerRepository.findByUserAndTicket(user, ticket).orElse(null);
        return Map.of("isFollowed", utf != null);
    }

    @Override
    public Map<String, String> followTicketForOtherUser(Long ticketId, Long userId) {
        UserEntity loggedUser = authService.getLoggedUser();
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFound("User not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        UserTicketFollower utf = userTicketFollowerRepository.findByUserAndTicket(user, ticket).orElse(null);
        if (utf != null) {
            return Map.of("message", "Ticket already followed");
        } else {
            UserTicketFollower userTicketFollower = new UserTicketFollower();
            userTicketFollower.setUser(user);
            userTicketFollower.setTicket(ticket);
            userTicketFollowerRepository.save(userTicketFollower);
            //TODO: Send notification to user that someone(loggedUser) made him follow a ticket
            return Map.of("message", "Ticket assigned to " + user.getEmail());
        }
    }

    @Override
    public List<UserResponseDto> getFollowersByTicketId(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        List<UserTicketFollower> utfs = userTicketFollowerRepository.findAllByTicket(ticket);

        return utfs.stream().map(utf -> UserMapper.mapToUserResponseDto(utf.getUser())).collect(Collectors.toList());
    }

    @Override
    public StatsResponseDto getStats() {
        UserEntity user = authService.getLoggedUser();
        Long totalTickets = ticketRepository.count();
        Long openedToday = ticketRepository.countByCreatedAtAfter(Timestamp.valueOf(java.time.LocalDate.now().atStartOfDay()));
        Long closedToday = ticketRepository.countByClosedAtAfter(Timestamp.valueOf(java.time.LocalDate.now().atStartOfDay()));
        Long openedTodayUser = ticketRepository.countByOpenedByAndCreatedAtAfter(user, Timestamp.valueOf(java.time.LocalDate.now().atStartOfDay()));
        Long closedTodayUser = ticketRepository.countByClosedByAndClosedAtAfter(user, Timestamp.valueOf(java.time.LocalDate.now().atStartOfDay()));
        Long commentedTodayUser = commentRepository.countDistinctTicketsCommentedToday(user, Timestamp.valueOf(java.time.LocalDate.now().atStartOfDay()));
        Long openFollowed = userTicketFollowerRepository.countByUserAndTicket_IsOpen(user, Boolean.TRUE);

        return StatsResponseDto.builder()
                .totalTickets(totalTickets)
                .openedToday(openedToday)
                .closedToday(closedToday)
                .openedTodayUser(openedTodayUser)
                .closedTodayUser(closedTodayUser)
                .commentedTodayUser(commentedTodayUser)
                .openFollowed(openFollowed)
                .build();
    }

    @Override
    public Page<TicketResponseDto> getFollowedTickets(int pageNumber, int pageSize, String sort) {
        UserEntity user = authService.getLoggedUser();

        Sort sortType = sort.equals("dateDesc")
                ? Sort.by("ticket.createdAt").descending()
                : Sort.by("ticket.createdAt").ascending();

        Page<UserTicketFollower> response = userTicketFollowerRepository
                .findAllByUserAndTicket_IsOpen(user, Boolean.TRUE, PageRequest.of(pageNumber, pageSize, sortType));

        return new PageImpl<>(
                response.getContent().stream()
                        .map(userTicketFollower -> ticketMapper.mapToTicketResponseDto(userTicketFollower.getTicket()))
                        .collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize, sortType),
                response.getTotalElements()
        );
    }

    @Override
    public Map<String, String> followTicket(Long ticketId) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        UserTicketFollower utf = userTicketFollowerRepository.findByUserAndTicket(user, ticket).orElse(null);
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
        UserTicketFollower utf = userTicketFollowerRepository.findByUserAndTicket(user, ticket).orElse(null);
        if (utf != null) {
            userTicketFollowerRepository.delete(utf);
            return Map.of("message", "Ticket unfollowed");
        } else {
            return Map.of("message", "Ticket not followed");
        }
    }

    @Override
    public Page<TicketResponseDto> searchTickets(SearchTicketRequestDto searchTicketRequestDto,
                                                 int pageNumber,
                                                 int pageSize) {


        Timestamp createdAfterTimestamp = searchTicketRequestDto.getCreatedAfter() != null
                ? Timestamp.valueOf(searchTicketRequestDto.getCreatedAfter().atStartOfDay()) : null;
        Timestamp createdBeforeTimestamp = searchTicketRequestDto.getCreatedBefore() != null
                ? Timestamp.valueOf(searchTicketRequestDto.getCreatedBefore().atTime(23, 59, 59)) : null;

        Specification<Ticket> spec = Specification.where(TicketSpecification.hasCustomerId(searchTicketRequestDto.getCustomerId()))
                .and(TicketSpecification.hasTicketId(searchTicketRequestDto.getTicketId()))
                .and(TicketSpecification.hasCustomerEmail(searchTicketRequestDto.getCustomerEmail()))
                .and(TicketSpecification.hasCustomerPhone(searchTicketRequestDto.getCustomerPhone()))
                .and(TicketSpecification.hasContent(searchTicketRequestDto.getContent()))
                .and(TicketSpecification.isOpen(searchTicketRequestDto.getIsOpen()))
                .and(TicketSpecification.isFollowed(authService.getLoggedUser(), searchTicketRequestDto.getIsFollowed()))
                .and(TicketSpecification.hasChannel(searchTicketRequestDto.getChannel()))
                .and(TicketSpecification.hasCategoryId(searchTicketRequestDto.getCategoryId()))
                .and(TicketSpecification.hasSubcategoryId(searchTicketRequestDto.getSubcategoryId()))
                .and(TicketSpecification.hasPriority(searchTicketRequestDto.getPriority()))
                .and(TicketSpecification.hasOpenedById(searchTicketRequestDto.getOpenedById()))
                .and(TicketSpecification.hasClosedById(searchTicketRequestDto.getClosedById()))
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
