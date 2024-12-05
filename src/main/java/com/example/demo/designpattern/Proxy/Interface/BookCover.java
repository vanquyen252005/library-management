package com.example.demo.designpattern.Proxy.Interface;

import javafx.scene.image.ImageView;

public interface BookCover {
    void display(ImageView imageView);
    String getISBN();
    String getTitle();
}
