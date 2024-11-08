package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);

    Page<TicketResponseDto> getFollowedTickets(int userId, int pageNumber, int pageSize);

    Map<String, String> followTicket(Long ticketId);

    Map<String, String> unfollowTicket(Long ticketId);

    Page<TicketResponseDto> searchTickets(Long ticketId, Long customerId, String customerEmail, String customerPhone, String content, Boolean isOpen, Channel channel, Long categoryId, Long subcategoryId, Priority priority, Long openedById, Long closedById, LocalDate createdAfter, LocalDate createdBefore, int pageNumber, int pageSize);

    TicketResponseDto getTicketById(Long ticketId);
}
