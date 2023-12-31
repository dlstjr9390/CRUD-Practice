package com.sparta.board_crud.post.service;

import com.sparta.board_crud.comment.dto.CommentResponseDto;
import com.sparta.board_crud.comment.entity.Comment;
import com.sparta.board_crud.comment.repository.CommentRepository;
import com.sparta.board_crud.post.dto.PostListResponseDto;
import com.sparta.board_crud.post.dto.PostRequestDto;
import com.sparta.board_crud.post.dto.PostResponseDto;
import com.sparta.board_crud.post.entity.Post;
import com.sparta.board_crud.post.repository.PostRepository;
import com.sparta.board_crud.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    public List<PostListResponseDto> getPostList(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postList = postRepository.findAll(pageable);

        return postList.map(PostListResponseDto::new).getContent();
    }

    public PostResponseDto getPost(Long id, int page, int size, String sortBy, Boolean isAsc) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        List<CommentResponseDto> commentResponseDtoList = getComments(id,page,size,sortBy,isAsc);

        return new PostResponseDto(post,commentResponseDtoList);
    }

    @Transactional
    public String updatePost(Long id, PostRequestDto postRequestDto,User user) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        if(!post.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalArgumentException("본인이 작성한 글만 수정이 가능합니다.");
        }
        post.update(postRequestDto);

        return "게시물 수정 완료";
    }

    @Transactional
    public String deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        if(!post.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalArgumentException("본인이 작성한 글만 삭제가 가능합니다.");
        }
        postRepository.delete(post);

        return "게시물 삭제 완료";
    }

    public List<CommentResponseDto> getComments(Long id, int page, int size, String sortBy, Boolean isAsc){
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Comment> commentList = commentRepository.findAllByPostId(id,pageable);

        return commentList.map(CommentResponseDto::new).getContent();
    }

    public LinkedHashMap<String, String> refineErrors(Errors errors){
        LinkedHashMap<String, String> error = new LinkedHashMap<>();
        error.put(errors.getFieldError().getField(),errors.getFieldError().getDefaultMessage());

        return error;
    }
}
