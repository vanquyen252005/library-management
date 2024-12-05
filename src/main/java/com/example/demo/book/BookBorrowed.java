
package com.example.demo.book;

public class BookBorrowed extends Book {
    private String borrowDate;
    private String returnDate;

    public BookBorrowed() {

    }
    public BookBorrowed(String ISBN, String borrow_date, String return_date) {
        this.setISBN(ISBN);
        this.borrowDate = borrow_date;
        this.returnDate = return_date;
    }


    public BookBorrowed(String title, String publisher, String publishYear, String author, String isbn, String borrowDate, String returnDate) {
        this.setTitle(title);
        this.setPublisher(publisher);
        this.setAuthor(author);
        this.setISBN(isbn);
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public void setBorrowDate(String Borrow_date) {
        this.borrowDate = Borrow_date;
    }

    public void setReturnDate(String Return_date) {
        this.returnDate = Return_date;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}