package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);

    Page<TicketResponseDto> getFollowedTickets(int pageNumber, int pageSize);

    Map<String, String> followTicket(Long ticketId);

    Map<String, String> unfollowTicket(Long ticketId);

    Page<TicketResponseDto> searchTickets(Long ticketId, Long customerId, String customerEmail, String customerPhone, String content, Boolean isOpen, Channel channel, Long categoryId, Long subcategoryId, Priority priority, Long openedById, Long closedById, LocalDate createdAfter, LocalDate createdBefore, int pageNumber, int pageSize);

    TicketResponseDto getTicketById(Long ticketId);

    Page<TicketResponseDto> getFollowedTicketsByUserId(Long userId, int pageNumber, int pageSize);

    Map<String, Boolean> isTicketFollowed(Long ticketId);

    Map<String, String> followTicketForOtherUser(Long ticketId, Long userId);

    List<UserResponseDto> getFollowersByTicketId(Long ticketId);
}
