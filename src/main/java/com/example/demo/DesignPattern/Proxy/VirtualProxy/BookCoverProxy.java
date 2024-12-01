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

    private void loadImage(ImageView imageView, String imageUrl) {
        Task<Image> imageTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (InputStream input = connection.getInputStream()) {
                        return new Image(input);
                    } catch (IOException e) {
                        System.err.println("Error loading image stream: " + e.getMessage());
                        return null;
                    }
                } else {
                    System.err.println("Error loading image: Server returned HTTP response code: " + responseCode);
                    return null;
                }
            }
        };

        imageTask.setOnSucceeded(event -> {
            Image image = imageTask.getValue();
            if (image != null) {
                imageView.setImage(image);
            }
        });

        imageTask.setOnFailed(event -> {
            System.err.println("Failed to load image: " + imageTask.getException().getMessage());
        });

        new Thread(imageTask).start();  // Start the task in a new thread
    }


    @Override
    public void display(ImageView imageView) {
        loadImage( imageView,bookImage);
        if (realBookCover == null) {
            realBookCover = new RealBookCover(ISBN, bookTitle, bookImage);
        }
        realBookCover.display(imageView);
    }

}
