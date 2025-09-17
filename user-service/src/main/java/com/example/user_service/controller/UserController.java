package com.example.user_service.controller;

import com.example.user_service.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        System.out.println("Received request for user ID: " + userId);
        return getMockUser(userId);
    }

    private User getMockUser(Long userId) {
        switch (userId.intValue()) {
            case 1:
                return new User(1L, "Никита Анциферов", "ул. Пушкина, д.1, кв.1", "+79244444444", "1QueenMoncler1@gmail.com");
            case 2:
                return new User(2L, "Nikita Antsiferov", "ул. Пушкина, д.2, кв.2", "+79266666666", "2QueenMoncler2@gmail.com");
            default:
                return new User(userId, "отсутствует информация",
                        "отсутствует информация",
                        "отсутствует информация",
                        "отсутствует информация");
        }
    }
}