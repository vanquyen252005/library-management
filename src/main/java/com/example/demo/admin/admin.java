package com.example.demo.admin;

import com.example.demo.user.User;

import java.sql.ResultSet;
import java.util.List;

public class admin extends User  {
    private static jdbc Request = new jdbc();
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
                    super.setPhone(resultSet.getString("phone"));
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        return false;
    }
    public List<com.example.demo.admin.Request> getRequestBook() {
        List<com.example.demo.admin.Request> ans = Request.getPendingRequests();
        return ans;
    }

}