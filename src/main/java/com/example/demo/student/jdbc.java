package com.example.demo.student;

import com.example.demo.book.Book;
import com.example.demo.book.Book_borrowed;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class jdbc {
    private static jdbc instance;
     private Connection connection;
     private Statement statement;

     private jdbc() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookdb",
                    "root",
                    "123456789"
            );

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized jdbc getInstance() {
        if (instance == null) {
            instance = new jdbc();
        }
        return instance;
    }

    public ResultSet getData(String username_, String password_) {

        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM userdata WHERE username = ? AND password = ?";


            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setString(1, username_);
            pstmt.setString(2, password_);


            resultSet = pstmt.executeQuery();
            System.out.println(query);
            if (resultSet != null) {
                System.out.println("hehe");
            } else {
                System.out.println("connect fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean addStudentData(Student newUser) {
        String id = newUser.getId();                     // Gán id
        String username_ = newUser.getUsername();         // Gán username
        String password_ = newUser.getPassword();         // Gán password
        String name = newUser.getName();                 // Gán name
        String role = newUser.getRole();                 // Gán role
        String phone = newUser.getPhone();               // Gán phone
        String classname = newUser.getClassname();       // Gán classname
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO userdata ( username, password, name, role, phone, classname) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, username_);   // Gán giá trị cho username
            pstmt.setString(2, password_);   // Gán giá trị cho password
            pstmt.setString(3, name);       // Gán giá trị cho name
            pstmt.setString(4, role);       // Gán giá trị cho role
            pstmt.setString(5, phone);      // Gán giá trị cho phone
            pstmt.setString(6, classname);  // Gán giá trị cho classname

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getStudentData(String op, String info) {
        ResultSet resultSet = null;
        try {
            info = "'%" + info + "%'";
            String query = "SELECT * FROM userdata WHERE " + op + " LIKE " + info + " and role = 'Student';";
            System.out.println(query);
            resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                System.out.println("OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean deleteStudent(String id) {
        String query = "DELETE FROM userdata WHERE id = ?";

        try {

            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setString(1, id);


            int rowsAffected = pstmt.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Student with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No student found with ID " + id + ".");
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Book_borrowed> loadBorrowedBook(int user_id) {
        ArrayList<Book_borrowed> listBook = new ArrayList<>();
        String query = "SELECT distinct  " +
                "    bb.book_id, " +
                "    b.Title, " +
                "    bb.borrow_date,  " +
                "    rr.request_date, " +
                "    b.Author, " +
                "    b.PublishYear, " +
                "    b.Publisher " +
                "FROM " +
                "    book_borrowed bb " +
                "LEFT JOIN  " +
                "    books b ON bb.book_id = b.ISBN " +
                "LEFT JOIN " +
                "    request_return_book rr ON bb.book_id = rr.book_id AND bb.user_id = rr.user_id " +
                "WHERE " +
                "    bb.user_id = ? ";

        try {
            System.out.println(query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, user_id);


            ResultSet resultSet = pstmt.executeQuery();


            while (resultSet.next()) {
                Book_borrowed book = new Book_borrowed();

                book.setISBN(resultSet.getString("book_id"));
                book.setTitle(resultSet.getString("Title"));
                book.setAuthor(resultSet.getString("Author"));
                book.setPublishYear(resultSet.getString("PublishYear"));
                book.setPublisher(resultSet.getString("Publisher"));
                book.setBorrow_date(resultSet.getString("borrow_date"));
                book.setReturn_date(resultSet.getString("request_date"));
                listBook.add(book);
            }

        } catch (SQLException e) {
            System.out.println("khong load duoc BroowedBook");
            e.printStackTrace();
        }
        return listBook;
    }
  public void updatePassWord(String userId,String passWord) {
        String query = "UPDATE userdata SET password = ? WHERE id = ? ";
      try {
          System.out.println(query);
          PreparedStatement pstmt = connection.prepareStatement(query);
          pstmt.setString(1,passWord);
          pstmt.setInt(2,parseInt(userId));
          int rowsAffected = pstmt.executeUpdate();
          if (rowsAffected > 0) {
              System.out.println("Cập nhật mật khẩu thành công cho userId: " + userId);
          } else {
              System.out.println("Không tìm thấy userId: " + userId);
          }
          pstmt.close();

      } catch (SQLException e) {
          System.out.println("khong update duoc new PassWord");
          e.printStackTrace();
      }

  }

    public boolean updateUserProfile(String userId, String username, String name, String phone, String userClass) {
        if (userId == null || userId.trim().isEmpty()) {
            System.out.println("id nguoi dung no duoc rong");
            return false;
        }


        StringBuilder queryBuilder = new StringBuilder("UPDATE userdata SET ");
        boolean hasUpdates = false;


        if (username != null && !username.trim().isEmpty()) {
            queryBuilder.append("username = ?, ");
            hasUpdates = true;
        }
        if (name != null && !name.trim().isEmpty()) {
            queryBuilder.append("name = ?, ");
            hasUpdates = true;
        }
        if (phone != null && !phone.trim().isEmpty()) {
            queryBuilder.append("phone = ?, ");
            hasUpdates = true;
        }
        if (userClass != null && !userClass.trim().isEmpty()) {
            queryBuilder.append("classname = ?, ");
            hasUpdates = true;
        }

        if (!hasUpdates) {
            System.out.println("Không có trường nào được cung cấp để cập nhật.");
            return false;
        }


        queryBuilder.setLength(queryBuilder.length() - 2); // Xóa dấu ", "
        queryBuilder.append(" WHERE id = ?");

        try {
            PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString());


            int index = 1;
            if (username != null && !username.trim().isEmpty()) {
                stmt.setString(index++, username);
            }
            if (name != null && !name.trim().isEmpty()) {
                stmt.setString(index++, name);
            }
            if (phone != null && !phone.trim().isEmpty()) {
                stmt.setString(index++, phone);
            }
            if (userClass != null && !userClass.trim().isEmpty()) {
                stmt.setString(index++, userClass);
            }
            stmt.setInt(index, Integer.parseInt(userId));


            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật dữ liệu thành công!");
                return true;
            } else {
                System.out.println("Không tìm thấy người dùng với ID: " + userId);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean CheckExistedUsername(String data) {
        String query = "SELECT COUNT(*) FROM userdata WHERE username = ?";

        try {

            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setString(1, data);


            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public boolean CheckExistedEmail(String data) {
        String query = "SELECT COUNT(*) FROM userdata email = ?";

        try {

            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setString(1, data);


            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public boolean CheckExistedName(String data) {
        String query = "SELECT COUNT(*) FROM userdata WHERE name = ?";

        try {

            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setString(1, data);


            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Student LoadUserInfor(int user_id) {
        Student user = null;

        String query = "SELECT id, username, password, name, role, phone, classname FROM userdata WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user_id); // Gán giá trị cho tham số id
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                user = new Student();
                user.setID(resultSet.getString("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setRole(resultSet.getString("role"));
                user.setPhone(resultSet.getString("phone"));
                user.setClassname(resultSet.getString("classname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean insertReturnRequest(String bookId, int userId) {
        String query = "INSERT INTO request_return_book (book_id, user_id, request_date, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setString(1,bookId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, getCurrentDate());
            pstmt.setString(4, "Pending");

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức để lấy ngày hiện tại theo định dạng yyyy-MM-dd
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}

