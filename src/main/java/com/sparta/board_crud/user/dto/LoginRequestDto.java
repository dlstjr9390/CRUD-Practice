package com.sparta.board_crud.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
