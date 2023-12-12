package com.sparta.board_crud.user.repository;

import com.sparta.board_crud.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
