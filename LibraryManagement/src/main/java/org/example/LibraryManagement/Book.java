package org.example.LibraryManagement;

public class Book {
    private String ISBN;
    private String Title;
    private String Author;
    private String Publisher;
    private String PublishYear;

    public Book(String ISBN, String Title, String Author, String Publisher, String PublishYear) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.PublishYear = PublishYear;
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
}
