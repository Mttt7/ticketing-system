package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);
}
