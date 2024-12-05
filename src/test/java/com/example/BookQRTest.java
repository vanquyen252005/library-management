package com.example;

import com.example.demo.book.Book;
import com.example.demo.book.BookQR;
import com.google.zxing.WriterException;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BookQRTest {

    private BookQR bookQR;

    @BeforeEach
    public void setUp() {
        bookQR = new BookQR();
    }

    @Test
    public void testGenerateQRCodeImage() throws WriterException {
        Book book = new Book("978-3-16-148410-0", "Sample Title", "Sample Author", "Sample Publisher", "2024");
        String text = book.toString();  // Dùng kết quả từ phương thức toString()

        BufferedImage qrCodeImage = BookQR.QRCodeGenerator.generateQRCodeImage(text);

        // Đảm bảo mã QR được tạo ra
        assertNotNull(qrCodeImage, "QR Code image should not be null.");
        assertEquals(200, qrCodeImage.getWidth(), "QR code width should be 200 pixels.");
        assertEquals(200, qrCodeImage.getHeight(), "QR code height should be 200 pixels.");
    }

    @Test
    public void testCreateQRCodeImageView() {
        Book book = new Book("978-3-16-148410-0", "Sample Title", "Sample Author", "Sample Publisher", "2024");
        String text = book.toString();  // Dùng kết quả từ phương thức toString()

        ImageView imageView = BookQR.createQRCodeImageView(text);

        // Kiểm tra xem ImageView có được tạo ra không
        assertNotNull(imageView, "ImageView should not be null.");
        assertEquals(200, imageView.getFitWidth(), "ImageView width should be 200 pixels.");
        assertEquals(200, imageView.getFitHeight(), "ImageView height should be 200 pixels.");
    }

    @Test
    public void testGenerateQRCodeImage_withEmptyText() {
        Book emptyBook = new Book("", "", "", "", "");
        String emptyText = emptyBook.toString();  // Dùng kết quả từ phương thức toString()

        assertThrows(WriterException.class, () -> {
            BookQR.QRCodeGenerator.generateQRCodeImage(emptyText);
        }, "Should throw WriterException when generating QR code with empty book details.");
    }
}
