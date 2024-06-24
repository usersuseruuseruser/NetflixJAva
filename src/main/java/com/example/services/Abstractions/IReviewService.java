package com.example.services.Abstractions;

import com.example.dto.ReviewAssignDto;
import com.example.dto.ReviewDto;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface IReviewService {
    List<ReviewDto> getReviewsByContentId(long contentId, int offset, int limit);

    int getReviewsCountByContentId(long contentId);

    void assignReview(long userId, ReviewAssignDto reviewDto);
}
