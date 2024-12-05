package com.example.demo.admin;

import com.example.demo.user.User;

import java.sql.ResultSet;
import java.util.List;

/**
 * Class represents an admin user which inherits from the User class.
 * It includes methods for logging in and retrieving pending book requests.
 */
public class Admin extends User {

    private static final Jdbc Request = new Jdbc();

    /**
     * Default constructor for the admin class.
     */
    public Admin() {
    }

    /**
     * Constructor for the admin class with specified user details.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @param name     The name of the admin.
     * @param role     The role of the admin.
     * @param phone    The phone number of the admin.
     */
    public Admin(String username, String password, String name, String role, String phone) {
        super(username, password, name, role, phone);
    }

    /**
     * Method to log in an admin user by validating username and password.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return True if the login is successful, otherwise false.
     */
    @Override
    public boolean loginAdmin(String username, String password) {
        try {
            ResultSet resultSet = Request.getData(username, password);
            while (resultSet.next()) {
                if (resultSet.getString("role").equals("admin")) {
                    super.setID(resultSet.getString("id"));
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
    }

    /**
     * Method to get a list of pending book requests for the admin.
     *
     * @return A list of pending requests.
     */
    public List<com.example.demo.admin.Request> getRequestBook() {
        return Request.getPendingRequests();
    }
}
