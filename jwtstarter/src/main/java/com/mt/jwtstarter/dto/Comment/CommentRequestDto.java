package com.mt.jwtstarter.dto.Comment;

import com.mt.jwtstarter.enums.CommentType;
import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private Long ticketId;
    private Long userId;
    private CommentType commentType;
}
