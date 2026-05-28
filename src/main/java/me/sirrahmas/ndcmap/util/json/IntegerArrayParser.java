package me.sirrahmas.ndcmap.util.json;

import org.json.JSONArray;

// Utility class that reads an array of integers from a JSON file.
public class IntegerArrayParser extends AbstractJSONArrayParser<Integer> {
    
    @Override
    Integer getElement(JSONArray jsonData, int index) {
        return jsonData.getInt(index);
    };
}