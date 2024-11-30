package com.example.demo.DesignPattern.Proxy.RealSubjects;

import com.example.demo.DesignPattern.Proxy.Interface.BookCover;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;


public class RealBookCover implements BookCover {
    private String bookTitle;
    private String bookImage;
    private String ISBN;

    public RealBookCover(String ISBN, String bookTitle, String bookImage) {
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.ISBN = ISBN;
    }

    @Override
    public void display(ImageView imageView) {}

    @Override
    public String getISBN() {
        return ISBN;
    }

    @Override
    public String getTitle() {
        return bookTitle;
    }
}
