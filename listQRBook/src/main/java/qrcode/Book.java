package qrcode;

public class Book {
    private String ISBN;
    private String BookTitle;
    private String Author;
    private String year;

    public Book(String ISBN, String BookTitle, String Author, String year) {
        this.ISBN = ISBN;
        this.BookTitle = BookTitle;
        this.Author = Author;
        this.year = year;
    }

    public String getBookInfo() {
        return "Title: " + ISBN + "\n" +
                "Author: " + BookTitle + "\n" +
                "ISBN: " + Author + "\n" +
                "year: " + year ;
    }
}
