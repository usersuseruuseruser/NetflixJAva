package com.example.Controllers;

import com.example.dto.PersonalInfoDto;
import com.example.dto.ReviewDto;
import com.example.dto.UserReviewDto;
import com.example.filter.JwtAuthentication;
import com.example.services.Abstractions.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-personal-info")
    public PersonalInfoDto getPersonalInfo() {
        Long userId = getUserIdFromAuthentication();
        return userService.getPersonalInfo(userId);
    }

    @PatchMapping("/change-profile-picture")
    public PersonalInfoDto changeProfilePicture(@RequestParam("image") MultipartFile image) {
        Long userId = getUserIdFromAuthentication();
        return userService.changeProfilePicture(userId, image);
    }
    @GetMapping("/get-reviews-pages-count")
    public int getReviewsPagesCount() {
        Long userId = getUserIdFromAuthentication();
        return userService.getReviewsPagesCount(userId);
    }
    @GetMapping("/get-reviews")
    public List<UserReviewDto> getReviews(int page, String input) {
        if (page == 0){
            page = 1;
        }
        Long userId = getUserIdFromAuthentication();
        return userService.getReviews(userId, page, input);
    }
    public Long getUserIdFromAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();

        if (principal instanceof JwtAuthentication jwtAuth) {
            return jwtAuth.getUserId();
        }

        throw new IllegalStateException("The principal is not a JwtAuthentication");
    }
}
