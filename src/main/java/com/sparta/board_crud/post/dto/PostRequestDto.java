package com.sparta.board_crud.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 5, message = "제목의 길이는 1에서 500 사이여야 합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    @Size(min = 1, max = 5000, message = "내용의 길이는 1에서 5000사이여야 합니다.")
    private String content;
}
