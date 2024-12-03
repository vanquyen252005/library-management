package com.example.demo.book;


public class Comment {
    private int id;
    private String bookId;
    private int userId;
    private String content;
    private String date;
    private Integer parentCommentId; // Optional to handle null values

    // Constructor
    public Comment(int id, String bookId, int userId, String content, Integer parentCommentId, String date) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // toString() method for debugging or display
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", parentCommentId=" + parentCommentId +
                ",date=" + date +
                '}';
    }
}

