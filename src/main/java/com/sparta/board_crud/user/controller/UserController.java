package com.sparta.board_crud.user.controller;

import com.sparta.board_crud.user.dto.LoginRequestDto;
import com.sparta.board_crud.user.dto.LoginResponseDto;
import com.sparta.board_crud.user.dto.SignupRequestDto;
import com.sparta.board_crud.user.entity.User;
import com.sparta.board_crud.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userService.refineErrors(errors));
        }
        LoginResponseDto loginResponseDto = userService.signup(requestDto);
        return ResponseEntity.status(loginResponseDto.getCode()).body(loginResponseDto.getMsg());
    }

    @PostMapping("/check")
    private String checkUsername(@RequestParam(name = "username") String username){

        return userService.checkUsername(username);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        LoginResponseDto loginResponseDto = userService.login(requestDto,response);
        return ResponseEntity.status(loginResponseDto.getCode()).body(loginResponseDto.getMsg());
    }
}
