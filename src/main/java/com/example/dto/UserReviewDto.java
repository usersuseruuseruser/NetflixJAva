package com.example.dto;

import java.time.LocalDateTime;

public class UserReviewDto {
    public long id;
    public boolean isPositive;
    public String name;
    public int score;
    public String text;
    public String contentName;
    public LocalDateTime writtenAt;
}

