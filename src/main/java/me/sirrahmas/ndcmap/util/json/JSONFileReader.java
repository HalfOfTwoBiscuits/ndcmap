package me.sirrahmas.ndcmap.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Utility class used to read an ArrayList of JSONObjects from a JSON file.
public class JSONFileReader {
    
    // Load JSON objects from the provided JSON file in the resources directory.
    public ArrayList<JSONObject> readJSON(String filePath) throws IOException, JSONException {

        StringBuilder sb = new StringBuilder();

        // Read the contents of the given file path in resources directory.
        try (
            InputStream i = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            InputStreamReader r = new InputStreamReader(i);
            BufferedReader br = new BufferedReader(r);
        )
            {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }

        String fileContents = sb.toString();
        
        // Get JSON data.
        JSONArray jsonData = new JSONArray(fileContents);

        // Convert to an ArrayList of JSONObjects for easy iteration.
        JSONObjectArrayParser p = new JSONObjectArrayParser();
        return p.parse(jsonData);
    }
}