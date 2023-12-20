package com.sparta.board_crud.comment.entity;

import com.sparta.board_crud.comment.dto.CommentRequestDto;
import com.sparta.board_crud.global.common.Timestamp;
import com.sparta.board_crud.post.entity.Post;
import com.sparta.board_crud.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    public Comment(CommentRequestDto commentRequestDto, Post post, User user) {
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.post = post;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
