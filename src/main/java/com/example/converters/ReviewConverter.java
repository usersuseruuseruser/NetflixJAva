package com.example.converters;

import com.example.dto.ReviewAssignDto;
import com.example.dto.ReviewDto;
import com.example.models.Review;

import java.util.stream.Collectors;

public class ReviewConverter {
    public static ReviewDto convertToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.id = review.id;
        reviewDto.user = UserConverter.convertToDto(review.user);
        reviewDto.score = review.score;
        reviewDto.writtenAt = review.writtenAt;
        reviewDto.isPositive = review.isPositive;
        reviewDto.text = review.text;
        reviewDto.comments = review.comments.stream().map(CommentConverter::convertToDto).toList();
        return reviewDto;
    }
    public static Review convertToEntity(ReviewAssignDto reviewDto) {
        Review review = new Review();
        review.score = reviewDto.score;
        review.isPositive = reviewDto.isPositive;
        review.text = reviewDto.text;
        review.movieId = reviewDto.contentId;
        return review;
    }
}
