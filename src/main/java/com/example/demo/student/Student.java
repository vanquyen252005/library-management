package com.example.demo.student;

import com.example.demo.book.BookBorrowed;
import com.example.demo.user.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Student extends User {
    ArrayList<BookBorrowed> BorrowingBook = new ArrayList<>();
    private String className;
    private static Jdbc Request = Jdbc.getInstance();
    public Student() {
        super();
    }

    public void setBorrowingBook(ArrayList<BookBorrowed> borrowingBook) {
        BorrowingBook = borrowingBook;
    }

    public Student( String username, String password, String name, String role, String phone, String className) {
        super(username, password, name, role, phone);
        this.className = className;
        // this.BorrowingBook = Request.loadBorrowedBook(parseInt(this.getId()));
    }
    public Student(String id, String username, String name) {
        super(id, username, name);
        // this.BorrowingBook = Request.loadBorrowedBook(parseInt(id));
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getClassName() {
        return className;
    }
    public ArrayList<BookBorrowed> getBorrowingBook() {
        return BorrowingBook;
    }
    @Override
    public boolean loginAdmin(String username, String password) {
        try{
            ResultSet resultSet = Request.getData(username, password);
            while (resultSet.next() != false) {
                if (resultSet.getString("role").equals("student")) {
                    super.setID(resultSet.getString("id"));
                    super.setUsername(resultSet.getString("username"));
                    super.setPassword(resultSet.getString("password"));
                    super.setName(resultSet.getString("name"));
                    super.setRole(resultSet.getString("role"));
                    super.setPhone(resultSet.getString("phone"));
                    this.setClassName(resultSet.getString("classname"));
                    this.BorrowingBook = Request.loadBorrowedBook(parseInt(this.getId()));
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
            System.out.println("unable to connect");
            throw new RuntimeException(e);
        }
    }
    public Student getStudent() {
        return this;
    }

    public static List<Student> getStudentBy(String op, String infor) {
        List<Student> arr = new ArrayList<>();
        try{
            System.out.println(op);
            ResultSet resultSet = Request.getStudentData(op, infor);
            while (resultSet.next() != false) {
                Student cur = new Student();
                cur.setID(resultSet.getString("id"));
                cur.setUsername(resultSet.getString("username"));
                cur.setPassword(resultSet.getString("password"));
                cur.setName(resultSet.getString("name"));
                cur.setRole(resultSet.getString("role"));
                cur.setPhone(resultSet.getString("phone")); // Chuyển đổi kiểu nếu cần
                cur.setClassName(resultSet.getString("classname"));
                cur.setBorrowingBook(Request.loadBorrowedBook(parseInt(cur.getId())));
                arr.add(cur);
            }
            return arr;
        } catch (Exception e) {
            System.out.println("found false here");
            throw new RuntimeException(e);
        }
    }
    public static boolean addStudent(Student newStudent) {
        return Request.addStudentData(newStudent);
    }
    public boolean deleteStudent(String id) {
        return Request.deleteStudent(id);
    }

    public boolean updateUserProfile(String userId, String username, String name, String phone, String userClass) {
        return Request.updateUserProfile(userId,username,name,phone,userClass);
    }

    public void TakeBorrowedBookList(Student student) {
        this.BorrowingBook = Request.loadBorrowedBook(parseInt(student.getId()));
    }
    public void updatePassWord(String userId, String password) {
        Request.updatePassWord(userId, password);
    }
}