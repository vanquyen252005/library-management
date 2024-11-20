package com.example.demo.book;

public class Book_borrowed extends Book {
     private String borrow_date;
     private String return_date;
      public Book_borrowed() {

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
