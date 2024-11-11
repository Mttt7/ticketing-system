package com.mt.jwtstarter.mapper;

import com.mt.jwtstarter.dto.Comment.CommentRequestDto;
import com.mt.jwtstarter.dto.Comment.CommentResponseDto;
import com.mt.jwtstarter.model.Comment;
import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.repository.TicketRepository;
import com.mt.jwtstarter.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentMapper {
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final AuthService authService;

    public CommentResponseDto mapToCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .ticketId(comment.getTicket().getId())
                .commentType(comment.getCommentType())
                .author(UserMapper.mapToUserResponseDto(comment.getAuthor()))
                .build();
    }

    public Comment mapToComment(CommentRequestDto commentRequestDto) {
        UserEntity user = authService.getLoggedUser();
        Ticket ticket = ticketRepository.findById(commentRequestDto.getTicketId()).orElseThrow(
                () -> new RuntimeException("Ticket not found"));

        return Comment.builder()
                .content(commentRequestDto.getContent())
                .ticket(ticket)
                .author(user)
                .commentType(commentRequestDto.getCommentType())
                .build();
    }
}
