package com.example.repository;

import com.example.dto.AssignCommentDto;
import com.example.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository{
    long assignComment(long userId, long reviewId, AssignCommentDto commentDto);
}
