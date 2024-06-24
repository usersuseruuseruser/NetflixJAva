package com.example.services.Abstractions;

import com.example.models.CommentNotification;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface INotificationService {
    void setNotificationReaded(long notificationId);
    CommentNotification getCommentNotification(long commentId);
    List<CommentNotification> getAllUserCommentNotifications(long userId);

}
