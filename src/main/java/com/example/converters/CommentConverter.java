package com.example.converters;

import com.example.dto.AssignCommentDto;
import com.example.dto.CommentDto;
import com.example.models.Comment;

public class CommentConverter {
    public static CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.id = comment.id;
        commentDto.user = UserConverter.convertToDto(comment.user);
        commentDto.text = comment.text;
        commentDto.writtenAt = comment.writtenAt;
        return commentDto;
    }

    public static Comment convertToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.id = commentDto.id;
        comment.user = UserConverter.convertToEntity(commentDto.user);
        comment.text = commentDto.text;
        comment.writtenAt = commentDto.writtenAt;
        return comment;
    }

    public static Comment convertToEntity(AssignCommentDto commentDto, long reviewId, long userId) {
        Comment comment = new Comment();
        comment.text = commentDto.text;
        comment.reviewId = reviewId;
        comment.userId = userId;
        return comment;
    }
}
