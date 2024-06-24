package com.example.services;

import com.example.models.CommentNotification;
import com.example.services.Abstractions.INotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class NotificationService implements INotificationService {
//    private CommentNotificationRepository commentNotificationRepository;
//
//    public NotificationService(CommentNotificationRepository commentNotificationRepository) {
//        this.commentNotificationRepository = commentNotificationRepository;
//    }
//
//    @Override
//    public void setNotificationReaded(long notificationId) {
//        commentNotificationRepository.setNotificationReaded(notificationId);
//    }
//
//    @Override
//    public CommentNotification getCommentNotification(long commentId) {
//        return commentNotificationRepository.getCommentNotificationById(commentId);
//    }
//
//    @Override
//    public List<CommentNotification> getAllUserCommentNotifications(long userId) {
//        return commentNotificationRepository.getAllByUserId(userId);
//    }
//}
