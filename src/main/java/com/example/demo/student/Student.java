package com.example.demo.student;

import com.example.demo.book.Book_borrowed;
import com.example.demo.user.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Student extends User {
    ArrayList<Book_borrowed> BorrowingBook = new ArrayList<>();
    private String classname;
    private static jdbc Request = new jdbc();
    public Student() {
        super();
    }

    public void setBorrowingBook(ArrayList<Book_borrowed> borrowingBook) {
        BorrowingBook = borrowingBook;
    }

    public Student( String username, String password, String name, String role, String phone, String classname) {
        super(username, password, name, role, phone);
        this.classname = classname;
       // this.BorrowingBook = Request.loadBorrowedBook(parseInt(this.getId()));
    }
    public Student(String id, String username, String name) {
        super(id, username, name);
       // this.BorrowingBook = Request.loadBorrowedBook(parseInt(id));
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getClassname() {
        return classname;
    }
    public ArrayList<Book_borrowed> getBorrowingBook() {
        return BorrowingBook;
    }
    @Override
    public boolean login(String username, String password) {
        try{
            ResultSet resultSet = Request.getData(username, password);
            while (resultSet.next() != false) {
                if (resultSet.getString("role").equals("Student")) {
                    super.setID(resultSet.getString("id"));
                    super.setUsername(resultSet.getString("username"));
                    super.setPassword(resultSet.getString("password"));
                    super.setName(resultSet.getString("name"));
                    super.setRole(resultSet.getString("role"));
                    super.setPhone(resultSet.getString("phone"));
                    this.setClassname(resultSet.getString("classname"));
                    this.BorrowingBook = Request.loadBorrowedBook(parseInt(this.getId()));
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
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
                cur.setClassname(resultSet.getString("classname"));
                cur.setBorrowingBook(Request.loadBorrowedBook(parseInt(cur.getId())));
                arr.add(cur);
            }
//            for(Student a:arr) {
//                System.out.println(a.getUsername() + " " + a.getName());
//            }
            return arr;
        } catch (Exception e) {
            System.out.println("found false here");
            throw new RuntimeException(e);
        }
    }
    public static void addStudent(Student newStudent) {
        Request.addStudentData(newStudent);
    }
    public void deleteStudent(String id) {
        Request.deleteStudent(id);
    }
    public void updateUserProfile(String userId, String username, String name, String phone, String userClass) {
        Request.updateUserProfile(userId,username,name,phone,userClass);
    }

    // retake the borrowed list
    public void TakeBorrowedBookList(Student student) {
        this.BorrowingBook = Request.loadBorrowedBook(parseInt(student.getId()));
    }

}