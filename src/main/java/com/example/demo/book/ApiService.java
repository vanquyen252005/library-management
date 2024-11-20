package com.example.demo.book;



import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class ApiService {


    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyAvh6cdJ6Uj23QMazu-EAYdWciALHtixmY"; // Your API key

//    public static void main(String[] args) {
//        String query = "mathematics"; // Your search query
//        searchBooks(query);
//    }

    public static List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        try {
            // Construct the request URL
            String url = API_URL + query + "&key=" + API_KEY;

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