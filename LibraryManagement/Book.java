package org.example.LibraryManagement;

public class Book {
    private String id;            // ID của sách, có thể là khóa chính trong cơ sở dữ liệu
    private String title;      // Tên sách
    private String author;     // Tác giả của sách

    // Constructor
    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Phương thức để hiển thị thông tin sách
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

