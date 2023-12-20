package com.sparta.board_crud.comment.dto;

import com.sparta.board_crud.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private String content;
    private String username;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
    }
}
