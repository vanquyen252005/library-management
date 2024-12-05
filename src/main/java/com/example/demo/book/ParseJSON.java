package com.example.demo.book;

import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJSON {

    public List<Book> parseJSON(String json) {
        List<Book> bookList = new ArrayList<>();

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
                int quantity = Integer.parseInt(extractQuantity(item)); // Added method to extract quantity

                // Create a new Book object and set its fields
                Book book = new Book();
                book.setISBN(ISBN);
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setPublishYear(publishYear);
                book.setImage(image);
                book.setQuantity(quantity); // Set the quantity

                // Add the book to the list
                bookList.add(book);
            }
        }
        return bookList;
    }

    public String extractISBN(JSONObject item) {
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

    public String extractImageLink(JSONObject volumeInfo) {
        if (volumeInfo.has("imageLinks")) {
            return volumeInfo.getJSONObject("imageLinks").optString("thumbnail", "No Image Available");
        }
        return "No Image Available";
    }

    public String extractQuantity(JSONObject item) {
        JSONObject saleInfo = item.optJSONObject("saleInfo");
        if (saleInfo != null) {
            return saleInfo.optString("quantity", "Unknown Quantity"); // Replace with the actual path if available
        }
        return "Unknown Quantity";
    }
}
