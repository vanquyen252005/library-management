package com.example.demo.book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJSON {

    public List<RequestBook> parseJSON(String json) {
        List<RequestBook> books = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray itemsArray = jsonObject.optJSONArray("items");

        if (itemsArray != null) {
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = item.optJSONObject("volumeInfo");

                // Extract fields from JSON
                String ISBN = extractISBN(item);
                String title = volumeInfo.optString("title", "No Title");
                String author = volumeInfo.optJSONArray("authors") != null ? volumeInfo.getJSONArray("authors").optString(0) : "Unknown Author";
                String publisher = volumeInfo.optString("publisher", "Unknown Publisher");
                String publishYear = volumeInfo.optString("publishedDate", "Unknown Year");
                String image = extractImageLink(volumeInfo);
                String quantity = "Unknown"; // Set quantity to a default value if it's not available from the API

                // Create a new Book object and set its fields
                RequestBook book = new RequestBook();
                book.setISBN(ISBN);
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setPublishYear(publishYear);
                book.setImage(image);
//                book.setQuantity(quantity);

                // Add the book to the list
                books.add(book);
            }
        }
        return books;
    }

    // Helper method to extract ISBN
    private String extractISBN(JSONObject item) {
        if (item.has("volumeInfo")) {
            JSONArray industryIdentifiers = item.getJSONObject("volumeInfo").optJSONArray("industryIdentifiers");
            if (industryIdentifiers != null) {
                for (int j = 0; j < industryIdentifiers.length(); j++) {
                    JSONObject identifier = industryIdentifiers.getJSONObject(j);
                    if ("ISBN_13".equals(identifier.optString("type"))) {
                        return identifier.optString("identifier");
                    }
                }
            }
        }
        return "Unknown ISBN";
    }

    // Helper method to extract image link
    private String extractImageLink(JSONObject volumeInfo) {
        if (volumeInfo.has("imageLinks")) {
            return volumeInfo.getJSONObject("imageLinks").optString("thumbnail", "No Image Available");
        }
        return "No Image Available";
    }
}