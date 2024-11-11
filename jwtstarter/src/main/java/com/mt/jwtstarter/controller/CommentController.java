package com.mt.jwtstarter.controller;


import com.mt.jwtstarter.dto.Comment.CommentRequestDto;
import com.mt.jwtstarter.dto.Comment.CommentResponseDto;
import com.mt.jwtstarter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<Page<CommentResponseDto>> getAllComments(@RequestParam Long ticketId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(commentService.getAllComments(ticketId, pageNumber, pageSize));
    }

    @PostMapping("/")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.createComment(commentRequestDto));
    }
}
