package com.example.repository;

import com.example.converters.CommentConverter;
import com.example.dto.AssignCommentDto;
import com.example.models.Comment;
import com.example.models.CommentNotification;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Repository
@Primary
public class CommentRepositoryImpl implements CommentRepository{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    @Transactional
    public long assignComment(long userId, long reviewId, AssignCommentDto commentDto) {
        Comment comment = CommentConverter.convertToEntity(commentDto, reviewId, userId);
        comment.writtenAt = LocalDateTime.now();
        entityManager.persist(comment);
        return comment.id;
    }
}
