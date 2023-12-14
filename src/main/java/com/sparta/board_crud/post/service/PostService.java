package com.sparta.board_crud.post.service;

import com.sparta.board_crud.post.dto.PostListResponseDto;
import com.sparta.board_crud.post.dto.PostRequestDto;
import com.sparta.board_crud.post.entity.Post;
import com.sparta.board_crud.post.repository.PostRepository;
import com.sparta.board_crud.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public String createPost(PostRequestDto postRequestDto, User user) {
        if(postRequestDto.getTitle()== null){
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
        if(postRequestDto.getContent() == null){
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
        if(user==null){
            throw new IllegalArgumentException("다시 로그인 해주세요.");
        }
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
        return "게시물 생성 완료";
    }

    public List<PostListResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostListResponseDto> postListResponseDtoList = postList.stream()
                .map(PostListResponseDto::new)
                .toList();
        return postListResponseDtoList;
    }

    public LinkedHashMap<String, String> refineErrors(Errors errors){
        LinkedHashMap<String, String> error = new LinkedHashMap<>();
        error.put(errors.getFieldError().getField(),errors.getFieldError().getDefaultMessage());

        return error;
    }

}
