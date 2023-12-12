package com.sparta.board_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardCrudApplication.class, args);
    }

}
