package com.sparta.board_crud.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
