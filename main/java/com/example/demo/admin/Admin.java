package com.example.demo.admin;

import com.example.demo.student.Student;
import com.example.demo.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private jdbc Request = new jdbc();
    //private Admin user = new Admin();

    public Admin() {
        super();
    }

    @Override
    public boolean login(String username, String password){
        try{
            ResultSet resultSet = Request.getData(username, password);
            while (resultSet.next() != false) {
                if (resultSet.getString("role").equals("admin")) {
                    super.setID(resultSet.getString("id"));
                    super.setUsername(resultSet.getString("username"));
                    super.setPassword(resultSet.getString("password"));
                    super.setName(resultSet.getString("name"));
                    super.setRole(resultSet.getString("role"));
                    super.setPhone(resultSet.getString("phone")); // Chuyển đổi kiểu nếu cần
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        return false;
    }
    public List<Student> getStudentBy(String op, String infor) {
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
                    arr.add(cur);
            }
//            for(Student a:arr) {
//                System.out.println(a.getUsername() + " " + a.getName());
//            }
            return arr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
