package com.sparta.board_crud.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
}
