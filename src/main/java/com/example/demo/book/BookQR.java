package com.example.demo.book;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class BookQR {

    private static final int QR_WIDTH = 200;
    private static final int QR_HEIGHT = 200;


    public class QRCodeGenerator {

        public static BufferedImage generateQRCodeImage(String text) throws WriterException {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_WIDTH,QR_HEIGHT);

            // Tạo mã QR dưới dạng BufferedImage
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        }
    }

    public static ImageView createQRCodeImageView(String text) {
        try {
            BufferedImage bufferedImage = QRCodeGenerator.generateQRCodeImage(text);

            // Ghi BufferedImage vào ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);

            // Tạo javafx.scene.image.Image từ ByteArrayInputStream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            Image qrImage = new Image(inputStream);

            // Tạo ImageView và đặt ảnh mã QR vào
            ImageView imageView = new ImageView(qrImage);
            imageView.setFitWidth(QR_WIDTH);
            imageView.setFitHeight(QR_HEIGHT);
            return imageView;

        } catch (WriterException e) {
            System.err.println("Could not generate QR Code: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

