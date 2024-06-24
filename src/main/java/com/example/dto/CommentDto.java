package com.example.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class CommentDto {
    public long id;
    public UserDto user;
    public String text;
    public LocalDateTime writtenAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getWrittenAt() {
        return writtenAt;
    }

    public void setWrittenAt(LocalDateTime writtenAt) {
        this.writtenAt = writtenAt;
    }
}
