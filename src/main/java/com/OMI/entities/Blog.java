package com.OMI.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    private String author;

    private String createdBy;

    private String blogType;

    private String blogCategory;

    // store multiple image URLs as a list
    @ElementCollection
    @CollectionTable(name = "blog_images", joinColumns = @JoinColumn(name = "blog_id"))
    @Column(name = "image_url")
    private List<String> imageList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public String getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(String blogCategory) {
        this.blogCategory = blogCategory;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imagesList) {
        this.imageList = imagesList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
