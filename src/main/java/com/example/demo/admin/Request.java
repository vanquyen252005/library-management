package com.example.demo.admin;

import java.util.List;

public class Request {
    private int id;
    private String bookId;
    private int userId;
    private String requestDate;

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    private String returnDate;
    private String status;
    private static jdbc RequestDB = new jdbc();
    private String type;
    public Request() {
    }

    public Request(int id, String bookId, int userId,String type, String requestDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.status = status;
        this.type = type;
    }

    public static List<Request> getRequest() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        RequestDB.updateRequestStatus(this);
    }
}
