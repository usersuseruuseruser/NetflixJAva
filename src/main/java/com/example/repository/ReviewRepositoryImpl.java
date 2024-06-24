package com.example.repository;

import com.example.converters.ReviewConverter;
import com.example.dto.ReviewAssignDto;
import com.example.dto.ReviewDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.example.models.Review;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@Primary
public class ReviewRepositoryImpl implements ReviewRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Review> getReviewsByContentId(long contentId, int offset, int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> review = query.from(Review.class);

        query.select(review).where(cb.equal(review.get("movieId"), contentId));

        List<Review> reviews = entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return reviews;
    }

    @Override
    public int getReviewsCountByContentId(long contentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Review> review = query.from(Review.class);

        query.select(cb.count(review)).where(cb.equal(review.get("movieId"), contentId));

        return Math.toIntExact(entityManager.createQuery(query).getSingleResult());
    }

    @Transactional
    @Override
    public void assignReview(long userId, ReviewAssignDto reviewDto) {
        Review review = ReviewConverter.convertToEntity(reviewDto);
        review.userId = userId;
        review.writtenAt = OffsetDateTime.now();
        entityManager.persist(review);
    }
}
