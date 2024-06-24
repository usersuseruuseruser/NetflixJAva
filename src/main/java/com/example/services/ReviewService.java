package com.example.services;

import com.example.converters.ReviewConverter;
import com.example.dto.ReviewAssignDto;
import com.example.dto.ReviewDto;
import com.example.models.Review;
import com.example.repository.ReviewRepository;
import com.example.services.Abstractions.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final MinioService minioService;

    public ReviewService(ReviewRepository reviewRepository,
                         MinioService minioService) {
        this.reviewRepository = reviewRepository;
        this.minioService = minioService;
    }

    public List<ReviewDto> getReviewsByContentId(long contentId, int offset, int limit) {
        List<ReviewDto> reviewDtos = reviewRepository.getReviewsByContentId(contentId, offset, limit).stream()
                .map(ReviewConverter::convertToDto)
                .collect(Collectors.toList());

        return reviewDtos;
    }

    public int getReviewsCountByContentId(long contentId) {
        return reviewRepository.getReviewsCountByContentId(contentId);
    }

    public void assignReview(long userId, ReviewAssignDto reviewDto) {
        reviewRepository.assignReview(userId, reviewDto);
    }
}
