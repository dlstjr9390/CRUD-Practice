package com.sparta.board_crud.user.controller;

import com.sparta.board_crud.user.dto.LoginRequestDto;
import com.sparta.board_crud.user.dto.LoginResponseDto;
import com.sparta.board_crud.user.dto.SignupRequestDto;
import com.sparta.board_crud.user.entity.User;
import com.sparta.board_crud.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto requestDto){
        userService.signup(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){

        return ResponseEntity.ok(userService.login(requestDto,response));
    }
}
