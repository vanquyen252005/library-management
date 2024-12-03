
package com.example.demo.book;

public class Book_borrowed extends Book {
    private String borrow_date;
    private String return_date;

    public Book_borrowed() {

    }
    public Book_borrowed(String ISBN, String borrow_date,String return_date) {
        this.setISBN(ISBN);
        this.borrow_date = borrow_date;
        this.return_date = return_date;
    }


    public Book_borrowed(String title, String publisher, String publishYear, String author, String isbn, String borrowDate, String returnDate) {
        this.setTitle(title);
        this.setPublisher(publisher);
        this.setAuthor(author);
        this.setISBN(isbn);
        this.borrow_date = borrowDate;
        this.return_date = returnDate;
    }

    public void setBorrow_date(String Borrow_date) {
        this.borrow_date = Borrow_date;
    }
    public void setReturn_date(String Return_date) {
        this.return_date = Return_date;
    }

    public String getBorrow_date() {
        return borrow_date;
    }

    public String getReturn_date() {
        return return_date;
    }
}