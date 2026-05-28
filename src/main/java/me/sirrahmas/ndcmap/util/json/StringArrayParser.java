package me.sirrahmas.ndcmap.util.json;

import org.json.JSONArray;

// Utility class that reads an array of strings from a JSON file.
public class StringArrayParser extends AbstractJSONArrayParser<String> {
    
    @Override
    String getElement(JSONArray jsonData, int index) {
        return jsonData.getString(index);
    };
}