package org.example.LibraryManagement;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class ParseJSON {
    public void parseJSON(String jsonResponse, javafx.scene.control.TextArea resultPrint) {
//        String json = """
//            {
//                "name": "John Doe",
//                "age": 30,
//                "isEmployed": true,
//                "skills": ["Java", "Python", "JavaScript"]
//            }
//            """;

        // Create a Gson instance
        // Create a Gson instance
        Gson gson = new Gson();

        // Parse the JSON string into a JsonObject
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        // Get the items array
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");

        // Check if the array is not empty
        if (itemsArray != null && itemsArray.size() > 0) {
            // Get the first item in the array
            JsonObject firstItem = itemsArray.get(0).getAsJsonObject();

            // Get the volumeInfo object
            JsonObject volumeInfo = firstItem.getAsJsonObject("volumeInfo");

            // Extract the title
            String title = volumeInfo.get("title").getAsString();

            // Print the title
            resultPrint.setText(title);
        } else {
            resultPrint.setText("No items found.");
        }
    }


}