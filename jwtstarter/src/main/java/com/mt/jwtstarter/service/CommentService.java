package com.mt.jwtstarter.service;

import com.mt.jwtstarter.dto.Comment.CommentRequestDto;
import com.mt.jwtstarter.dto.Comment.CommentResponseDto;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentResponseDto> getAllComments(Long ticketId, int pageNumber, int pageSize);

    CommentResponseDto createComment(CommentRequestDto commentRequestDto);
}
