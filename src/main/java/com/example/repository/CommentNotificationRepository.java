package com.example.repository;

import com.example.models.CommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//public interface CommentNotificationRepository extends JpaRepository<CommentNotification, Long>{
//    @Modifying
//    @Query(value = "update comment_notification set readed = true where id = ?1", nativeQuery = true)
//    void setNotificationReaded(long notificationId);
//
//    CommentNotification getCommentNotificationById(long commentId);
//
//    @Query(value = "select * from comment_notification cn join comment c on cn.comment_id = c.id where c.user_id = ?1", nativeQuery = true)
//    List<CommentNotification> getAllByUserId(long userId);
//}
