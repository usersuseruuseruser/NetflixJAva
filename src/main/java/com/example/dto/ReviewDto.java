package com.example.dto;

import com.example.models.User;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewDto {
    public long id;
    public UserDto user;
    public int score;
    public OffsetDateTime writtenAt;
    public  boolean isPositive;
    public String text;
    public List<CommentDto> comments = new ArrayList<>();
}
