package com.example.Controllers;

import com.example.filter.JwtAuthentication;
import com.example.models.CommentNotification;
import com.example.services.Abstractions.INotificationService;
import com.fasterxml.jackson.core.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RestController
public class NotificationController {
    private INotificationService notificationService;
    private SimpMessagingTemplate template;

    public NotificationController(INotificationService notificationService,
    SimpMessagingTemplate template) {
        this.notificationService = notificationService;
        this.template = template;
    }


    @GetMapping("comment/notifications")
    public List<CommentNotification> GetAllUsersNotifications() {
        long userId = getUserIdFromAuthentication();

        return notificationService.getAllUserCommentNotifications(userId);

    }

    @PostMapping("set/readed")
    public HttpEntity<?> setNotificationReaded(long notificationId) {
        notificationService.setNotificationReaded(notificationId);

        return HttpEntity.EMPTY;
    }


    @MessageMapping("/ReadNotification")
    @SendTo("/topic/DeleteNotification")
    public void ReadNotification(long notificationId, String body) {
        notificationService.setNotificationReaded(notificationId);

        template.convertAndSend("/topic/DeleteNotification", notificationId);
    }

    @MessageMapping("NotifyAboutComment")
    @SendTo("/topic/ReceiveNotification")
    public void notifyAboutComment(SimpMessageHeaderAccessor headerAccessor, String body) {
        var commentId = getCommentIdFromToken(body);

        var commentNotification = notificationService.getCommentNotification(commentId);

        template.convertAndSend("/topic/NotifyAboutComment", commentNotification);
    }


//    @MessageMapping("/test")
//    @SendTo("/topic/test")
//    public void Test(SimpMessageHeaderAccessor headerAccessor, String body) {
//        var userId = getUserIdFromToken(body);
//
//        template.convertAndSend("/topic/test", userId);
//    }

    public Long getUserIdFromAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();

        if (principal instanceof JwtAuthentication jwtAuth) {
            return jwtAuth.getUserId();
        }

        throw new IllegalStateException("The principal is not a JwtAuthentication");
    }
    public Long getCommentIdFromToken(String commentId) {
        Pattern pattern = Pattern.compile("\"commentId\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(commentId);

        if (matcher.find()) {
            commentId = matcher.group(1);
        }

        return Long.parseLong(commentId);
    }
    public Long getUserIdFromToken(String token) {
        Pattern pattern = Pattern.compile("\"token\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(token);

        if (matcher.find()) {
            token = matcher.group(1);
        }

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey("and0LnNlY3JldC5hY2Nlc3Muand0LnNlY3JldC5hY2Nlc3M")
                .build()
                .parseClaimsJws(token);

        String userId = jwsClaims.getBody().getSubject();

        return Long.parseLong(userId);
    }
}
