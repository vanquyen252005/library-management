package com.example.demo.admin;

import com.example.demo.HelloController;
import com.example.demo.student.Student;
import com.example.demo.user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class admin extends User  {
    private static jdbc Request = new jdbc();
    //private Admin user = new Admin();

    public admin() {
    }
    public admin( String username, String password, String name, String role, String phone) {
        super( username, password, name, role, phone);
    }
    @Override
    public boolean login(String username, String password) {
        try {
            ResultSet resultSet = Request.getData(username, password);
            while (resultSet.next() != false) {
                if (resultSet.getString("role").equals("admin")) {
                    //super.setID(resultSet.getString("id"));
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
    public ArrayList<RequestBook> inQueue() {
        ArrayList<RequestBook> ans = Request.getInQueue();
        return ans;
    }

}