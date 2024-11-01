package com.mt.jwtstarter.controller;


import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/")
    public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto) {
        return ResponseEntity.ok().body(ticketService.createTicket(ticketRequestDto));
    }
}
