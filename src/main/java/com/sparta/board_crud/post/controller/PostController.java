package com.sparta.board_crud.post.controller;

import com.sparta.board_crud.post.dto.PostListResponseDto;
import com.sparta.board_crud.post.dto.PostRequestDto;
import com.sparta.board_crud.post.dto.PostResponseDto;
import com.sparta.board_crud.post.service.PostService;
import com.sparta.board_crud.user.security.UserDetailsImpl;
import jakarta.validation.Valid;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequestDto postRequestDto,
                                        Errors errors,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(postService.refineErrors(errors));
        }
        try {
            return ResponseEntity.ok(postService.createPost(postRequestDto,userDetails.getUser()));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("")
    public List<PostListResponseDto> getPostList(@RequestParam("page") int page,
                                                 @RequestParam("size") int size,
                                                 @RequestParam("sortBy") String sortBy,
                                                 @RequestParam("isAsc") boolean isAsc
                                                 ){
        return postService.getPostList(page,size,sortBy,isAsc);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable(name = "postId") Long id){
        try {
            return ResponseEntity.ok(postService.getPost(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable(name = "postId") Long id,
                                      @Valid @RequestBody PostRequestDto postRequestDto,
                                      Errors errors,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){

        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(postService.refineErrors(errors));
        }
        try {
            return ResponseEntity.ok(postService.updatePost(id, postRequestDto, userDetails.getUser()));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "postId")Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){

        try {
            return ResponseEntity.ok(postService.deletePost(id,userDetails.getUser()));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
