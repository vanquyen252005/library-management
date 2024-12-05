package com.example;

import com.example.demo.book.ApiService;
import com.example.demo.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiServiceTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;

    @InjectMocks
    private ApiService apiService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchBooks_Success() throws IOException, InterruptedException {
        // Arrange
        String query = "isbn:9780134685991";  // Example ISBN
        String mockResponse = "{ \"items\": [ { \"volumeInfo\": { \"title\": \"Effective Java\", \"authors\": [\"Joshua Bloch\"], \"publisher\": \"Addison-Wesley\", \"publishedDate\": \"2018\", \"imageLinks\": { \"thumbnail\": \"http://example.com/image.jpg\" } } } ] }";

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(mockResponse);

        // Act
        List<Book> books = apiService.searchBooks(query);

        // Assert
        assertNotNull(books);
        assertEquals(1, books.size());  // We expect only one book to be returned
        Book book = books.get(0);
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals("Addison-Wesley", book.getPublisher());
        assertEquals("2018", book.getPublishYear());
        assertEquals("http://example.com/image.jpg", book.getImage());
    }

    @Test
    public void testSearchBooks_Failure_Non200Status() throws IOException, InterruptedException {
        // Arrange
        String query = "isbn:9780134685991";  // Example ISBN
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.statusCode()).thenReturn(500);  // Simulating server error

        // Act
        List<Book> books = apiService.searchBooks(query);

        // Assert
        assertNotNull(books);
        assertTrue(books.isEmpty());  // No books should be returned on error
    }

    @Test
    public void testSearchBooks_ExceptionHandling() throws IOException, InterruptedException {
        // Arrange
        String query = "isbn:9780134685991";  // Example ISBN
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException("Network error"));

        // Act & Assert
        List<Book> books = apiService.searchBooks(query);
        assertNotNull(books);
        assertTrue(books.isEmpty());  // The list should be empty if an exception occurs
    }
}
