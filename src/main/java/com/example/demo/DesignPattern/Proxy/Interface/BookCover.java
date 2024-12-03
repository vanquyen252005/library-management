package com.example.demo.DesignPattern.Proxy.Interface;

import javafx.scene.image.ImageView;

public interface BookCover {
    void display(ImageView imageView);
    String getISBN();
    String getTitle();
}
