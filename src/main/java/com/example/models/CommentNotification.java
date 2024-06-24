package com.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

public class CommentNotification {
    public long id;
    public boolean readed;

    public Comment comment;
}
