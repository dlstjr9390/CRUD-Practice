package com.sparta.board_crud.post.repository;

import com.sparta.board_crud.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

}
