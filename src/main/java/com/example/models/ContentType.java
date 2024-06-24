package com.example.models;

import javax.persistence.*;

@Entity
@Table(name = "content_types")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Long id;

    public String contentTypeName;
}
