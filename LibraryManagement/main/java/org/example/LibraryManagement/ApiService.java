package org.example.LibraryManagement;



import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ApiService {


    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyAvh6cdJ6Uj23QMazu-EAYdWciALHtixmY"; // Your API key

//    public static void main(String[] args) {
//        String query = "mathematics"; // Your search query
//        searchBooks(query);
//    }

    public static void searchBooks(String query, javafx.scene.control.TextArea resultPrint) {
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
                // Parse and display the results
//                System.out.println(response.body());
                ParseJSON hehe = new ParseJSON();
                hehe.parseJSON(response.body(), resultPrint);

            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}