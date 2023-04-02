package com.example.springboot.entity;

import lombok.Data;

import java.util.List;

@Data
public class Book extends BaseEntity {
    private String name;
    private String description;
    private String publishDate;
    private String author;
    private String publisher;
    private String category;
    private String bookNo;
    private String cover;
    private Integer score;
    private Integer nums;

    private List<String> categories;
}
