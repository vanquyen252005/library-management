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

    private static final String GOOGLE_BOOKS_API_KEY = "AIzaSyAvh6cdJ6Uj23QMazu-EAYdWciALHtixmY";
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";


    public static List<Book> searchBooks(String query) {
        List<Book> bookList = new ArrayList<>();
        try {
            String url = GOOGLE_BOOKS_API_URL + query + "&key=" + GOOGLE_BOOKS_API_KEY;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ParseJSON convertJSON = new ParseJSON();
                bookList = convertJSON.parseJSON(response.body());

            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return bookList;
    }
   }