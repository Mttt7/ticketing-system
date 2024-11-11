package com.mt.jwtstarter.dto.Comment;

import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.enums.CommentType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private UserResponseDto author;
    private Long ticketId;
    private CommentType commentType;
    private Timestamp createdAt;
}
