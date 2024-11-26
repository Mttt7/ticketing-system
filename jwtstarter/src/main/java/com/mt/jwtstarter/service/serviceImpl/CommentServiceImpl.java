package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Comment.CommentRequestDto;
import com.mt.jwtstarter.dto.Comment.CommentResponseDto;
import com.mt.jwtstarter.enums.NotificationType;
import com.mt.jwtstarter.mapper.CommentMapper;
import com.mt.jwtstarter.model.Comment;
import com.mt.jwtstarter.repository.CommentRepository;
import com.mt.jwtstarter.service.CommentService;
import com.mt.jwtstarter.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NotificationService notificationService;

    @Override
    public Page<CommentResponseDto> getAllComments(Long ticketId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Comment> comments = commentRepository.findAllByTicketId(ticketId, pageRequest);
        return new PageImpl<>(
                comments.getContent().stream().map(commentMapper::mapToCommentResponseDto).toList(),
                pageRequest,
                comments.getTotalElements()
        );
    }

    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.mapToComment(commentRequestDto);
        comment.getTicket().getFollowers().forEach(utf -> {
            if (!utf.getUser().getId().equals(comment.getAuthor().getId())) {
                notificationService.createNotification(comment.getTicket().getId(), NotificationType.NEW_COMMENT_ON_FOLLOWED_TICKET, utf.getUser().getId());
            }
        });
        return commentMapper.mapToCommentResponseDto(commentRepository.save(comment));
    }
}
