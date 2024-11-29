package com.example.demo.book;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class ApiService {

    private static final String GOOGLE_BOOKS_API_KEY = "AIzaSyAvh6cdJ6Uj23QMazu-EAYdWciALHtixmY"; // Thay bằng API Key thực tế của bạn
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    // Lấy URL ảnh bìa từ Google Books API theo ISBN
    public static String getGoogleBookImage(String ISBN) {
        String requestUrl = GOOGLE_BOOKS_API_URL + ISBN + "&key=" + GOOGLE_BOOKS_API_KEY;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(requestUrl))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray items = jsonResponse.optJSONArray("items");
            if (items != null && items.length() > 0) {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
                JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                if (imageLinks != null) {
                    return imageLinks.getString("thumbnail"); // Trả về link ảnh bìa
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy ảnh
    }

    public static String updateBookImage(String ISBN) { // Sử dụng "Image-URL-M" cho URL ảnh

        String newImageUrl = getGoogleBookImage(ISBN); // Lấy ảnh từ Google Books API
        if (newImageUrl != null) {
            System.out.println("Cập nhật ảnh bìa mới cho sách ISBN " + ISBN + ": " + newImageUrl);
            return newImageUrl;
        }
        System.out.println("Không tìm thấy ảnh thay thế cho ISBN " + ISBN + ". Sử dụng ảnh mặc định.");
        return "default_image_url.jpg"; // Thay bằng URL ảnh mặc định


    }

    public static List<RequestBook> searchBooks(String query) {
        List<RequestBook> books = new ArrayList<>();
        try {
            // Construct the request URL
            String url = GOOGLE_BOOKS_API_URL + query + "&key=" + GOOGLE_BOOKS_API_KEY;

            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response status code
            if (response.statusCode() == 200) {
                // Parse and map JSON response to a list of Book objects
                ParseJSON convertJSON = new ParseJSON();
                books = convertJSON.parseJSON(response.body());

            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return books;
    }
   }