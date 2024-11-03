package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);

    Page<TicketResponseDto> getFollowedTickets(int userId, int pageNumber, int pageSize);

    Map<String, String> followTicket(Long ticketId);

    Map<String, String> unfollowTicket(Long ticketId);
}
