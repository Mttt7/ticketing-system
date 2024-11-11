package com.mt.jwtstarter.service.serviceImpl;

import com.mt.jwtstarter.dto.Comment.CommentRequestDto;
import com.mt.jwtstarter.dto.Comment.CommentResponseDto;
import com.mt.jwtstarter.mapper.CommentMapper;
import com.mt.jwtstarter.model.Comment;
import com.mt.jwtstarter.repository.CommentRepository;
import com.mt.jwtstarter.service.CommentService;
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
        return commentMapper.mapToCommentResponseDto(commentRepository.save(comment));
    }

}
