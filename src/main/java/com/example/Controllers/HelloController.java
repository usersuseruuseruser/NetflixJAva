package com.example.Controllers;

import com.example.filter.JwtAuthentication;
import com.example.models.User;
import com.example.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HelloController {


    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

}