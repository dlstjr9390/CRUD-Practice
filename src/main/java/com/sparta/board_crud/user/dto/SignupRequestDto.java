package com.sparta.board_crud.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

@Getter
public class SignupRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[0-9A-Za-z]*$", message = "아이디는 3~16자 영어 소문자와 숫자만 입력 가능합니다.")
    @Size(min =3, max = 16)
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^[!-~]*$",
            message = "비밀번호는 4~16자 영어 대 소문자, 숫자, 특수문자만 입력 가능합니다.")
    @Size(min =4, max = 16)
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String checkedPassword;

    @Email(message = "옳지 않은 형식 입니다.")
    private String email;
}
