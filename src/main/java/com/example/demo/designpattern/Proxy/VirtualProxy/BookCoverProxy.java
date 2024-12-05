package com.example.demo.designpattern.Proxy.VirtualProxy;

import com.example.demo.designpattern.Proxy.Interface.BookCover;
import com.example.demo.designpattern.Proxy.RealSubjects.RealBookCover;
import javafx.scene.image.*;

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
