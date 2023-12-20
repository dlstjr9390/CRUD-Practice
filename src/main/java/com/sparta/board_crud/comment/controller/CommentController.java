package com.sparta.board_crud.comment.controller;

import com.sparta.board_crud.comment.dto.CommentRequestDto;
import com.sparta.board_crud.comment.dto.CommentResponseDto;
import com.sparta.board_crud.comment.service.CommentService;
import com.sparta.board_crud.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public void createComment(@PathVariable(name = "postId") Long id,
                              @RequestBody CommentRequestDto commentRequestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){

        commentService.createComment(id,commentRequestDto,userDetails.getUser());


    }

    @PatchMapping("/{postId}")
    public void updateComment(@PathVariable(name="postId") Long id,
                              CommentRequestDto commentRequestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){

        commentService.updateComment(id,commentRequestDto,userDetails.getUser()
        );
    }

}
