package com.sparta.board_crud.comment.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @Pattern(regexp = "^[!-~]*$")
    @Size(min =1, max = 100,message = "최대 100자까지 입력 가능합니다.")
    private String content;
}
