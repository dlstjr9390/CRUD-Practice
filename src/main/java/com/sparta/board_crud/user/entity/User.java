package com.sparta.board_crud.user.entity;

import com.sparta.board_crud.global.common.Timestamp;
import com.sparta.board_crud.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private UserRoleEnum userRole;


    public User(String username, String password, String email, UserRoleEnum userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = Objects.requireNonNullElse(userRole, UserRoleEnum.USER);
    }

}
