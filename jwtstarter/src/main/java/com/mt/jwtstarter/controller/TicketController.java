package com.mt.jwtstarter.controller;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
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

import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.getTicketById(ticketId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<TicketResponseDto>> getFollowedTicketsByUserId(@PathVariable Long userId, @RequestParam int pageNumber, @RequestParam int pageSize,
                                                                              @RequestParam String sort) {
        return ResponseEntity.ok().body(ticketService.getFollowedTicketsByUserId(userId, pageNumber, pageSize, sort));
    }

    @GetMapping("/followed")
    public ResponseEntity<Page<TicketResponseDto>> getFollowedTicketsForLoggedUser(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sort) {
        return ResponseEntity.ok().body(ticketService.getFollowedTickets(pageNumber, pageSize, sort));
    }

    @GetMapping("/{ticketId}/followers")
    public ResponseEntity<List<UserResponseDto>> getFollowersByTicketId(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.getFollowersByTicketId(ticketId));
    }

    @GetMapping("/is-followed/{ticketId}")
    public ResponseEntity<Map<String, Boolean>> isTicketFollowed(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.isTicketFollowed(ticketId));
    }

    @PostMapping("/follow/{ticketId}")
    public ResponseEntity<Map<String, String>> followTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.followTicket(ticketId));
    }

    @PostMapping("/unfollow/{ticketId}")
    public ResponseEntity<Map<String, String>> unfollowTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.unfollowTicket(ticketId));
    }

    @PostMapping("/follow/{ticketId}/{userId}")
    public ResponseEntity<Map<String, String>> followTicketForOtherUser(@PathVariable Long ticketId, @PathVariable Long userId) {
        return ResponseEntity.ok().body(ticketService.followTicketForOtherUser(ticketId, userId));
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
