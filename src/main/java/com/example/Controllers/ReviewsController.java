package com.example.Controllers;

import com.example.dto.ReviewAssignDto;
import com.example.dto.ReviewDto;
import com.example.filter.JwtAuthentication;
import com.example.repository.MovieRepository;
import com.example.repository.ReviewRepository;
import com.example.services.Abstractions.IContentService;
import com.example.services.Abstractions.IReviewService;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reviews")
@RestController
public class ReviewsController {
    private IReviewService reviewService;

    public ReviewsController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{contentId}")
    public List<ReviewDto> getReviewsByContentId(@PathVariable long contentId, @RequestParam int offset,
                                                 @RequestParam int limit) {
        return reviewService.getReviewsByContentId(contentId,offset,limit);
    }

    @GetMapping("count/{contentId}")
    public long getReviewsCountByContentId(@PathVariable long contentId) {
        return reviewService.getReviewsCountByContentId(contentId);
    }

    @PostMapping("/assign")
    public void assignReview(@RequestBody ReviewAssignDto reviewDto) {
        long userId = getUserIdFromAuthentication();
        reviewService.assignReview(userId, reviewDto);
    }
    public Long getUserIdFromAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();

        if (principal instanceof JwtAuthentication jwtAuth) {
            return jwtAuth.getUserId();
        }

        throw new IllegalStateException("The principal is not a JwtAuthentication");
    }

}
