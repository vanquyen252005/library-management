package com.example.demo.book;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String ISBN;
    private String Title;
    private String Author;
    private String Publisher;
    private String PublishYear;
    private String Image;
    private int quantity;
    private static Database Request = Database.getInstance();
    public Book() {
    }

    public Book(String ISBN, String title, String author, String publisher, String publishYear, String image) {
        this.ISBN = ISBN;
        Title = title;
        Author = author;
        Publisher = publisher;
        PublishYear = publishYear;
        this.Image = image;
    }

    public Book(String ISBN, String Title, String Author, String Publisher, String PublishYear) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.PublishYear = PublishYear;
    }

    public static List<Book> getBook(String searchText) {
        List<Book> bookList = new ArrayList<>();
        try{
            bookList = Request.searchDocuments(searchText);
            return bookList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Book> getBookDB(String searchText) {
        List<Book> bookList = new ArrayList<>();
        try{
            bookList = Request.searchDocumentsDB(searchText);
            return bookList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Book> getBookAPI(String searchText) {
        List<Book> bookList = new ArrayList<>();
        try{
            bookList = Request.searchDocumentsAPI(searchText);
            return bookList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Book> searchBooksByTitle(String keyword) {
        return Request.searchDocumentsDBSingleColumn("Title", keyword);
    }

    public static List<Book> searchBooksByAuthor(String keyword) {
        return Request.searchDocumentsDBSingleColumn("Author", keyword);
    }

    public static List<Book> searchBooksByPublisher(String keyword) {
        return Request.searchDocumentsDBSingleColumn("Publisher", keyword);
    }
    public static boolean addBook(Book newBook) {
        return Request.addDocument(newBook.getTitle()
                , newBook.getAuthor()
                , newBook.getPublishYear()
                , newBook.getPublisher()
                , newBook.getImage());
    }


        public static List<Book> getBooksByPage(int page, String text) {
            int pageSize = 10; // Số sách mỗi trang
            int offset = (page - 1) * pageSize;

            return Request.queryBooks(pageSize, offset, text);
    }

    public boolean updateBook(Book curBook) {
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

    public void setQuantity(int quantity) {this.quantity = quantity;}

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

    public Book getBook() {
        return this;
    }

    public ImageView loadImage() {
        try {
            String imageUrl = this.getImage();
            ImageView imageView = new ImageView();

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

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

    public Book(String ISBN, String title, String author, String publisher, String publishYear, String image, int quantity) {
        this.ISBN = ISBN;
        Title = title;
        Author = author;
        Publisher = publisher;
        PublishYear = publishYear;
        Image = image;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
