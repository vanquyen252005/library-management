package com.example.demo.book;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestBook {
    private String ISBN;
    private String Title;
    private String Author;
    private String Publisher;
    private String PublishYear;
    private String Image;
    private static ConnectDB Request = new ConnectDB();
    public RequestBook() {
    }

    public RequestBook(String ISBN, String title, String author, String publisher, String publishYear, String image) {
        this.ISBN = ISBN;
        Title = title;
        Author = author;
        Publisher = publisher;
        PublishYear = publishYear;
        this.Image = image;
    }

    public RequestBook(String ISBN, String Title, String Author, String Publisher, String PublishYear) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.PublishYear = PublishYear;
    }

    public static List<RequestBook> getBookDB(String searchText) {
        List<RequestBook> arr = new ArrayList<>();
        try{
            arr = Request.searchDocumentsDB(searchText);
//            for(Student a:arr) {
//                System.out.println(a.getUsername() + " " + a.getName());
//            }
            return arr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<RequestBook> getBookAPI(String searchText) {
        List<RequestBook> arr = new ArrayList<>();
        try{
            arr = Request.searchDocumentsAPI(searchText);
//            for(Student a:arr) {
//                System.out.println(a.getUsername() + " " + a.getName());
//            }
            return arr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Tìm kiếm theo Title
    public static List<RequestBook> searchBooksByTitle(String keyword) {
        return Request.searchDocumentsDBSingleColumn("Title", keyword);
    }

    // Tìm kiếm theo Author
    public static List<RequestBook> searchBooksByAuthor(String keyword) {
        return Request.searchDocumentsDBSingleColumn("Author", keyword);
    }

    // Tìm kiếm theo Publisher
    public static List<RequestBook> searchBooksByPublisher(String keyword) {
        return Request.searchDocumentsDBSingleColumn("Publisher", keyword);
    }
    public static boolean addBook(RequestBook newBook) {
        return Request.addDocument(newBook.getTitle()
                , newBook.getAuthor()
        , newBook.getPublishYear()
                , newBook.getPublisher()
        , newBook.getImage());
    }

    public boolean updateBook(RequestBook curBook) {
        return Request.editDocument(curBook.getISBN()
                , curBook.getTitle()
                , curBook.getAuthor()
                , curBook.getPublisher()
                , curBook.getPublishYear()
                , curBook.getImage());
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void deleteBook() {
        Request.deleteDocument(this.ISBN);
    }

    public RequestBook getBook() {
        return this;
    }
    public ImageView loadImage() {
        try {
            String imageUrl = this.getImage();
            ImageView imageView = new ImageView();
            // Tạo URL và kết nối
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lấy hình ảnh và gán cho ImageView
                InputStream input = connection.getInputStream();
                javafx.scene.image.Image image = new Image(input);
                imageView.setImage(image);
                return imageView;
            } else {
                System.err.println("Error loading image: Server returned HTTP response code: " + responseCode + " for URL: " + imageUrl);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        return null;
    }
    public List<Comment> getCommentList() {
        return Request.GetCommentList(ISBN);
    }
    public void getNewComment(){};
}
