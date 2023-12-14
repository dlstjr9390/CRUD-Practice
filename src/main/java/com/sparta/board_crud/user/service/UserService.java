package com.sparta.board_crud.user.service;

import com.sparta.board_crud.global.security.JwtUtil;
import com.sparta.board_crud.user.dto.LoginRequestDto;
import com.sparta.board_crud.user.dto.LoginResponseDto;
import com.sparta.board_crud.user.dto.SignupRequestDto;
import com.sparta.board_crud.user.entity.User;
import com.sparta.board_crud.user.entity.UserRoleEnum;
import com.sparta.board_crud.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password =passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        UserRoleEnum userRole = UserRoleEnum.USER;
        try {
            if(userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()){
                throw new IllegalArgumentException("중복된 아이디 혹은 이메일이 존재합니다.");
            }
            if(!Objects.equals(requestDto.getPassword(),requestDto.getCheckedPassword())){
                throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 다릅니다.");
            }
            if(requestDto.getPassword().contains(username)){
                throw new IllegalArgumentException("닉네임이 비밀번호에 포함되어 있습니다.");
            }
        } catch (IllegalArgumentException e){
            return new LoginResponseDto(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }

        User user = new User(username,password,email,userRole);
        userRepository.save(user);

        return new LoginResponseDto("회원가입 완료",HttpStatus.OK);
    }

    public String checkUsername(String username){
        if(userRepository.findByUsername(username).isPresent()){
            return "해당 아이디는 중복된 아이디입니다.";
        } else{
            return "사용 가능한 아이디 입니다.";
        }
    }

    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        try {
            User user = userRepository.findByUsername(username).orElseThrow(
                    ()-> new IllegalArgumentException("존재하지 않는 사용자 입니다.")
            );

            try {
                if(passwordEncoder.matches(password, user.getPassword())){
                    jwtUtil.addJwtToCookies(jwtUtil.createToken(user.getUsername(),user.getUserRole()),response);
                    return new LoginResponseDto(user, HttpStatus.OK);
                } else{
                    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                }
            } catch (IllegalArgumentException e){
                return new LoginResponseDto(e.getMessage(),HttpStatus.UNAUTHORIZED);
            }

        } catch (IllegalArgumentException e){
            return new LoginResponseDto(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    public LinkedHashMap<String, String> refineErrors(Errors errors){
        LinkedHashMap<String, String> error = new LinkedHashMap<>();
        error.put(errors.getFieldError().getField(),errors.getFieldError().getDefaultMessage());

        return error;
    }
}
