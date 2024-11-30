package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.dto.Ticket.SearchTicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.StatsResponseDto;
import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);

    Page<TicketResponseDto> getFollowedTickets(int pageNumber, int pageSize, String sort);

    Map<String, String> followTicket(Long ticketId);

    Map<String, String> unfollowTicket(Long ticketId);

    Page<TicketResponseDto> searchTickets(SearchTicketRequestDto searchTicketRequestDto, int pageNumber, int pageSize, String sortType);

    TicketResponseDto getTicketById(Long ticketId);

    Page<TicketResponseDto> getFollowedTicketsByUserId(Long userId, int pageNumber, int pageSize, String sort);

    Map<String, Boolean> isTicketFollowed(Long ticketId);

    Map<String, String> followTicketForOtherUser(Long ticketId, Long userId);

    List<UserResponseDto> getFollowersByTicketId(Long ticketId);

    StatsResponseDto getStats();

    TicketResponseDto closeTicket(Long ticketId);

    TicketResponseDto changeCategory(Long ticketId, Integer categoryId, Integer subcategoryId);
}
