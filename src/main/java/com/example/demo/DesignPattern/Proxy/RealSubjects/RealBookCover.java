package com.example.demo.DesignPattern.Proxy.RealSubjects;

import com.example.demo.DesignPattern.Proxy.Interface.BookCover;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RealBookCover implements BookCover {
    private String bookTitle;
    private String bookImage;
    private String ISBN;

    public RealBookCover(String ISBN, String bookTitle, String bookImage) {
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.ISBN = ISBN;
    }

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public void loadImage(final String imageUrl, final ImageView imageView) {
        executorService.submit(() -> {
            try {
                // Tạo URL và kết nối
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Lấy hình ảnh và kiểm tra kích thước
                    InputStream input = connection.getInputStream();
                    Image image = new Image(input);

                    // Kiểm tra kích thước ảnh
                    if (image.getHeight() < 50 || image.getWidth() < 50) {
                        System.out.println("using coverError with error: " + image.getHeight() + " " + image.getWidth());
                        // Sử dụng ảnh lỗi nếu ảnh nhỏ quá
                        String imagePath = getClass().getResource("/Picture/coverError.png").toExternalForm();
                        image = new Image(imagePath);
                    }

                    // Cập nhật UI (ImageView) sau khi tải xong, cần sử dụng Platform.runLater
                    Image finalImage = image;
                    Platform.runLater(() -> {
                        imageView.setImage(finalImage);
                        imageView.setFitWidth(160);
                        imageView.setFitHeight(220);
                    });

                } else {
                    System.err.println("Error loading image: Server returned HTTP response code: " + responseCode + " for URL: " + imageUrl);
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        });
    }



    @Override
    public void display(ImageView imageView) {
        loadImage(bookImage, imageView);
    }

    @Override
    public String getISBN() {
        return ISBN;
    }

    @Override
    public String getTitle() {
        return bookTitle;
    }
}
