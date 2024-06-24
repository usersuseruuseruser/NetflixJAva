package com.example.repository;

import com.example.dto.ReviewAssignDto;
import com.example.dto.ReviewDto;
import com.example.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ReviewRepository {

    List<Review> getReviewsByContentId(long contentId, int offset, int limit);
    int getReviewsCountByContentId(long contentId);
    void assignReview(long userId, ReviewAssignDto reviewDto);
}
