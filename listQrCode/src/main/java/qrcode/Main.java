package qrcode;
import com.google.zxing.WriterException;
import java.util.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/book";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");

            // Tạo một câu truy vấn
            String sql = "SELECT * FROM books"; // Thay "tên_bảng" bằng tên bảng của bạn
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Duyệt kết quả và in ra console
            while (resultSet.next()) {
                String ISBN = resultSet.getString("ISBN");
                String BookTitle = resultSet.getString("Book-Title");
                String Author = resultSet.getString("Book-Author");
                String year = resultSet.getString("Year-Of-Publication");
                // tao list book
                listBook.add(new Book(ISBN,BookTitle,Author,year));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kết nối thất bại!");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<listBook.size();i++) {
            String bookinfo = listBook.get(i).getBookInfo();
            String filepath = "book_qr_code" + (i + 1) + ".png";
            try {
                QRCodeGenerator.generateQRCodeImage(bookinfo, 350, 350, filepath);
                System.out.println("QR Code generated successfully at: " + filepath);
            } catch (WriterException | IOException e) {
                System.err.println("Could not generate QR Code: " + e.getMessage());
            }
        }
    }
}
