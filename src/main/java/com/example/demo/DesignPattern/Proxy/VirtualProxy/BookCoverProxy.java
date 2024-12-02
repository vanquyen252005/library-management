package com.example.demo.DesignPattern.Proxy.VirtualProxy;

import com.example.demo.DesignPattern.Proxy.Interface.BookCover;
import com.example.demo.DesignPattern.Proxy.RealSubjects.RealBookCover;
import javafx.concurrent.Task;
import javafx.scene.image.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BookCoverProxy implements BookCover {
    private RealBookCover realBookCover;
    private String ISBN;
    private String bookTitle;
    private String bookImage;


    @Override
    public String getISBN() {return ISBN;}

    @Override
    public String getTitle() {
        return bookTitle;
    }

    public BookCoverProxy(String ISBN, String bookTitle, String bookImage) {
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.ISBN = ISBN;
    }

    @Override
    public void display(ImageView imageView) {
        if (realBookCover == null) {
            realBookCover = new RealBookCover(ISBN, bookTitle, bookImage);
        }
        realBookCover.display(imageView);
    }

}
