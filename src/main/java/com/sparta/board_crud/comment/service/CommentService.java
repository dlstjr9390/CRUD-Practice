package com.sparta.board_crud.comment.service;

import com.sparta.board_crud.comment.dto.CommentRequestDto;
import com.sparta.board_crud.comment.dto.CommentResponseDto;
import com.sparta.board_crud.comment.entity.Comment;
import com.sparta.board_crud.comment.repository.CommentRepository;
import com.sparta.board_crud.post.entity.Post;
import com.sparta.board_crud.post.repository.PostRepository;
import com.sparta.board_crud.user.entity.User;
import com.sparta.board_crud.user.repository.UserRepository;
import com.sparta.board_crud.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public void createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
        Comment comment = new Comment(commentRequestDto,post,user);
        commentRepository.save(comment);

    }

    public void updateComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 댓글을 찾을 수 없습니다.")
        );
        if(!comment.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalArgumentException("본인의 댓글만 수정 가능합니다.");
        }
        comment.update(commentRequestDto);

    }
}
