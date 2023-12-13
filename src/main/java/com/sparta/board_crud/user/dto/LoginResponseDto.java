package com.sparta.board_crud.user.dto;

import com.sparta.board_crud.user.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginResponseDto {
    private HttpStatus code;
    private String msg;

    public LoginResponseDto(User user, HttpStatus code){
        this.code = code;
        this.msg = user.getUsername() + "님 환영합니다.";
    }

    public LoginResponseDto(String errorMessage,HttpStatus code){
        this.code = code;
        this.msg = errorMessage;
    }
}
