package com.sparta.board_crud.post.dto;

import com.sparta.board_crud.comment.dto.CommentResponseDto;
import com.sparta.board_crud.comment.entity.Comment;
import com.sparta.board_crud.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;

    public PostResponseDto(Post post,List<CommentResponseDto> commentResponseDtoList) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.commentList = commentResponseDtoList;
    }

}
