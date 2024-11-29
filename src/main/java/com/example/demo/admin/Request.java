package com.example.demo.admin;

import java.util.List;

public class Request {
    private int id;
    private String bookId;
    private int userId;
    private String requestDate;
    private String status;
    private static jdbc RequestDB = new jdbc();
    public Request() {
    }

    public Request(int id, String bookId, int userId, String requestDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.status = status;
    }

    public static List<com.example.demo.admin.Request> getRequest() {
        return RequestDB.getPendingRequests();
    }

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

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateRequestStatus() {
        RequestDB.updateRequestStatus(id, status);
    }
}
