package com.sparta.board_crud.post.entity;

import com.sparta.board_crud.global.common.Timestamp;
import com.sparta.board_crud.post.dto.PostRequestDto;
import com.sparta.board_crud.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(PostRequestDto postRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = user;
    }

}
