package com.example.Controllers;

import com.example.dto.AssignCommentDto;
import com.example.filter.JwtAuthentication;
import com.example.models.Comment;
import com.example.repository.CommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/comment")
@RestController
public class CommentController {
    private CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("/assign")
    public void assignComment(long reviewId, @RequestBody AssignCommentDto commentDto) {
        commentRepository.assignComment(getUserIdFromAuthentication(), reviewId, commentDto);
    }
    public Long getUserIdFromAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();

        if (principal instanceof JwtAuthentication jwtAuth) {
            return jwtAuth.getUserId();
        }

        throw new IllegalStateException("The principal is not a JwtAuthentication");
    }
}
