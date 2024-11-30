package com.mt.jwtstarter.controller;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.dto.Ticket.*;
import com.mt.jwtstarter.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{ticketId}/close")
    public ResponseEntity<TicketResponseDto> closeTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.closeTicket(ticketId));
    }

    @PatchMapping("/{ticketId}/{categoryId}/{subcategoryId}")
    public ResponseEntity<TicketResponseDto> changeCategory(@PathVariable Long ticketId, @PathVariable Integer categoryId, @PathVariable Integer subcategoryId) {
        return ResponseEntity.ok().body(ticketService.changeCategory(ticketId, categoryId, subcategoryId));
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

    @PostMapping("/my-departments")
    public ResponseEntity<Page<TicketResponseDto>> searchTicketsByDepartments(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortType, @RequestBody SearchTicketSimpleRequestDto searchTicketSimpleRequestDto) {
        return ResponseEntity.ok().body(ticketService.getTicketsByDepartments(pageNumber, pageSize, sortType, searchTicketSimpleRequestDto));
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

    @PostMapping("/search")
    public Page<TicketResponseDto> searchTickets(
            @RequestBody SearchTicketRequestDto searchTicketRequestDto,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "newest") String sortType) {

        return ticketService.searchTickets(searchTicketRequestDto, pageNumber, pageSize, sortType);
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<StatsResponseDto> getStats() {
        return ResponseEntity.ok().body(ticketService.getStats());
    }
}
