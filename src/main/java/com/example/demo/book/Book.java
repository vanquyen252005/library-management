package com.example.demo.book;

import com.example.demo.student.student;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String ISBN;
    private String Title;
    private String Author;
    private String Publisher;
    private String PublishYear;
    private String image;
    private static ConnectDB Request = new ConnectDB();
    public Book() {
    }

    public Book(String ISBN, String image, String publishYear, String publisher, String author, String title) {
        this.ISBN = ISBN;
        this.image = image;
        PublishYear = publishYear;
        Publisher = publisher;
        Author = author;
        Title = title;
    }

    public Book(String ISBN, String Title, String Author, String Publisher, String PublishYear) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.PublishYear = PublishYear;
    }

    public static List<Book> getBook(String searchText) {
        List<Book> arr = new ArrayList<>();
        try{
            arr = Request.searchDocuments(searchText);
//            for(Student a:arr) {
//                System.out.println(a.getUsername() + " " + a.getName());
//            }
            return arr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getPublishYear() {
        return PublishYear;
    }

    public void setPublishYear(String publishYear) {
        PublishYear = publishYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Publisher='" + Publisher + '\'' +
                ", PublishYear='" + PublishYear + '\'' +
                '}';
    }

    public void setImage(String image) {

    }

    public void deleteBook() {
        Request.deleteDocument(this.ISBN);
    }

    public Book getBook() {
        return this;
    }
}
