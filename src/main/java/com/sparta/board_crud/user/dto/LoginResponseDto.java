package com.sparta.board_crud.user.dto;

import com.sparta.board_crud.user.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String username;
    private String msg;

    public LoginResponseDto(User user){
        this.username = user.getUsername();
        this.msg = username + "님 환영합니다.";
    }
}
