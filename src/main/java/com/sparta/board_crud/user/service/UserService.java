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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password =passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        UserRoleEnum userRole = UserRoleEnum.USER;

        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("사용자가 이미 존재합니다.");
        }

        User user = new User(username,password,email,userRole);
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 사용자 입니다.")
        );

        if(passwordEncoder.matches(password, user.getPassword())){
            jwtUtil.addJwtToCookies(jwtUtil.createToken(user.getEmail(),user.getUserRole()),response);
            return new LoginResponseDto(user);
        } else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
