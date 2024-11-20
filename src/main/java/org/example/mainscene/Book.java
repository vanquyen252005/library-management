package org.example.mainscene;

public class Book {
    private String ISBN;
    private String Title;
    private String Author;
    private String Publisher;
    private String PublishYear;
    private String Image;
    private String quantity;

    public Book(){}

    public Book(String ISBN, String Title, String Author, String Publisher, String PublishYear, String image) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.PublishYear = PublishYear;
        this.Image = image;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPublishYear() {
        return PublishYear;
    }

    public void setPublishYear(String publishYear) {
        PublishYear = publishYear;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    public String getBookInfo() {
        return "Title: " + Title + "\n" +
                "Author: " + Author + "\n" +
                "ISBN: " + ISBN + "\n" +
                "Year: " + PublishYear;
    }

}

