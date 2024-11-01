package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.mapper.TicketMapper;
import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.repository.TicketRepository;
import com.mt.jwtstarter.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = ticketMapper.mapToTicket(ticketRequestDto);
        return ticketMapper.mapToTicketResponseDto(ticketRepository.save(ticket));
    }
}
