package com.mt.jwtstarter.controller;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import com.mt.jwtstarter.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<TicketResponseDto>> getFollowedTicketsByUserId(@PathVariable int userId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok().body(ticketService.getFollowedTickets(userId, pageNumber, pageSize));
    }

    @PostMapping("/follow/{ticketId}")
    public ResponseEntity<Map<String, String>> followTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.followTicket(ticketId));
    }

    @PostMapping("/unfollow/{ticketId}")
    public ResponseEntity<Map<String, String>> unfollowTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.unfollowTicket(ticketId));
    }

    @GetMapping("/search")
    public Page<TicketResponseDto> searchTickets(
            @RequestParam(required = false) Long ticketId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String customerEmail,
            @RequestParam(required = false) String customerPhone,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Boolean isOpen,
            @RequestParam(required = false) Channel channel,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long subcategoryId,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Long openedById,
            @RequestParam(required = false) Long closedById,
            @RequestParam(required = false) LocalDate createdAfter,
            @RequestParam(required = false) LocalDate createdBefore,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        return ticketService.searchTickets(ticketId, customerId, customerEmail, customerPhone, content, isOpen, channel,
                categoryId, subcategoryId, priority, openedById, closedById,
                createdAfter, createdBefore, pageNumber, pageSize);
    }
}
