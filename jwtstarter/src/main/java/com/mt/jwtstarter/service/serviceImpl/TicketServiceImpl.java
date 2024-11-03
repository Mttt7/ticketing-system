package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Ticket.TicketRequestDto;
import com.mt.jwtstarter.dto.Ticket.TicketResponseDto;
import com.mt.jwtstarter.mapper.TicketMapper;
import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.model.UserTicketFollower;
import com.mt.jwtstarter.repository.TicketRepository;
import com.mt.jwtstarter.repository.UserTicketFollowerRepository;
import com.mt.jwtstarter.service.AuthService;
import com.mt.jwtstarter.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final AuthService authService;
    private final UserTicketFollowerRepository userTicketFollowerRepository;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketMapper.mapToTicket(ticketRequestDto);
        Ticket ticketDb = ticketRepository.save(ticket);

        UserTicketFollower userTicketFollower = new UserTicketFollower();
        userTicketFollower.setUser(user);
        userTicketFollower.setTicket(ticketDb);
        userTicketFollowerRepository.save(userTicketFollower);

        return ticketMapper.mapToTicketResponseDto(ticketDb);
    }

    @Override
    public Page<TicketResponseDto> getFollowedTickets(int userId, int pageNumber, int pageSize) {
        UserEntity user = authService.getLoggedUser();
        Page<UserTicketFollower> response = userTicketFollowerRepository.findAllByUser(user, PageRequest.of(pageNumber, pageSize));

        return new PageImpl<>(
                response.getContent().stream().map(userTicketFollower -> ticketMapper.mapToTicketResponseDto(userTicketFollower.getTicket())).collect(Collectors.toList()),
                PageRequest.of(pageNumber, pageSize),
                response.getTotalElements()
        );
    }

    @Override
    public Map<String, String> followTicket(Long ticketId) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        UserTicketFollower utf =  userTicketFollowerRepository.findByUser(user);
        if(utf != null) {
            return Map.of("message", "Ticket already followed");
        } else {
            UserTicketFollower userTicketFollower = new UserTicketFollower();
            userTicketFollower.setUser(user);
            userTicketFollower.setTicket(ticket);
            userTicketFollowerRepository.save(userTicketFollower);
            return Map.of("message", "Ticket followed");
        }
    }

    @Override
    public Map<String, String> unfollowTicket(Long ticketId) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        UserTicketFollower utf =  userTicketFollowerRepository.findByUser(user);
        if(utf != null) {
            userTicketFollowerRepository.delete(utf);
            return Map.of("message", "Ticket unfollowed");
        } else {
            return Map.of("message", "Ticket not followed");
        }
    }
}
