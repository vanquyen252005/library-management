package com.example;

import com.example.demo.book.Book;
import com.example.demo.book.ParseJSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ParseJSONTest {

    private ParseJSON parseJSON;

    @BeforeEach
    public void setUp() {
        parseJSON = new ParseJSON();
    }

    // Test with a simple JSON input
    @Test
    public void testParseJSON() {
        // Example JSON string that mimics the structure of the Google Books API response
        String json = "{ \"items\": [ { \"volumeInfo\": { \"title\": \"Sample Book\", \"authors\": [\"Sample Author\"], \"publisher\": \"Sample Publisher\", \"publishedDate\": \"2024\", \"imageLinks\": { \"thumbnail\": \"http://example.com/thumbnail.jpg\" } }, \"industryIdentifiers\": [ { \"type\": \"ISBN_13\", \"identifier\": \"978-3-16-148410-0\" } ], \"saleInfo\": { \"quantity\": \"5\" } } ] }";

        // Parse the JSON
        List<Book> books = parseJSON.parseJSON(json);

        // Assert that the list contains the correct number of books (1 book in this case)
        assertEquals(1, books.size(), "The list should contain one book");

        // Get the book object from the list
        Book book = books.get(0);

        // Assert the values of the book's fields
        assertEquals("978-3-16-148410-0", book.getISBN(), "ISBN should match the extracted ISBN");
        assertEquals("Sample Book", book.getTitle(), "Title should match");
        assertEquals("Sample Author", book.getAuthor(), "Author should match");
        assertEquals("Sample Publisher", book.getPublisher(), "Publisher should match");
        assertEquals("2024", book.getPublishYear(), "Publish Year should match");
        assertEquals("http://example.com/thumbnail.jpg", book.getImage(), "Image URL should match");
        assertEquals(5, book.getQuantity(), "Quantity should be 5");
    }

    // Test with missing fields in JSON
    @Test
    public void testParseJSON_withMissingFields() {
        String json = "{ \"items\": [ { \"volumeInfo\": { \"title\": \"Incomplete Book\", \"authors\": [], \"publisher\": \"Unknown Publisher\" }, \"saleInfo\": { \"quantity\": \"0\" } } ] }";

        List<Book> books = parseJSON.parseJSON(json);

        assertEquals(1, books.size(), "The list should contain one book");

        Book book = books.get(0);

        assertEquals("Unknown ISBN", book.getISBN(), "ISBN should be 'Unknown ISBN'");
        assertEquals("Incomplete Book", book.getTitle(), "Title should match");
        assertEquals("Unknown Author", book.getAuthor(), "Author should default to 'Unknown Author'");
        assertEquals("Unknown Publisher", book.getPublisher(), "Publisher should default to 'Unknown Publisher'");
        assertEquals("Unknown Year", book.getPublishYear(), "Publish Year should default to 'Unknown Year'");
        assertEquals("No Image Available", book.getImage(), "Image should default to 'No Image Available'");
        assertEquals(0, book.getQuantity(), "Quantity should default to 0");
    }

    // Test with an empty JSON string
    @Test
    public void testParseJSON_emptyJSON() {
        String json = "{}";

        List<Book> books = parseJSON.parseJSON(json);

        assertTrue(books.isEmpty(), "The list should be empty for empty JSON input");
    }

    // Test with malformed JSON string
    @Test
    public void testParseJSON_malformedJSON() {
        String json = "{ \"items\": [ { \"volumeInfo\": { \"title\": \"Malformed Book\", \"authors\": [\"Malformed Author\"], \"publisher\": \"Malformed Publisher\" }";

        assertThrows(org.json.JSONException.class, () -> {
            parseJSON.parseJSON(json);
        }, "Should throw JSONException for malformed JSON input");
    }

    // Test extractISBN method with a valid ISBN
    @Test
    public void testExtractISBN_valid() {
        String json = "{ \"volumeInfo\": { \"industryIdentifiers\": [ { \"type\": \"ISBN_13\", \"identifier\": \"978-3-16-148410-0\" } ] } }";
        JSONObject item = new JSONObject(json);
        String isbn = parseJSON.extractISBN(item);

        assertEquals("978-3-16-148410-0", isbn, "ISBN should match the expected value");
    }

    // Test extractImageLink method with a valid image link
    @Test
    public void testExtractImageLink_valid() {
        String json = "{ \"volumeInfo\": { \"imageLinks\": { \"thumbnail\": \"http://example.com/thumbnail.jpg\" } } }";
        JSONObject volumeInfo = new JSONObject(json).getJSONObject("volumeInfo");
        String imageLink = parseJSON.extractImageLink(volumeInfo);

        assertEquals("http://example.com/thumbnail.jpg", imageLink, "Image link should match the expected value");
    }

    // Test extractQuantity method with a valid quantity
    @Test
    public void testExtractQuantity_valid() {
        String json = "{ \"saleInfo\": { \"quantity\": \"10\" } }";
        JSONObject item = new JSONObject(json);
        String quantity = parseJSON.extractQuantity(item);

        assertEquals("10", quantity, "Quantity should match the expected value");
    }
}
