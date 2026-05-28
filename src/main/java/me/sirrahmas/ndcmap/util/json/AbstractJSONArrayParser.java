package me.sirrahmas.ndcmap.util.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Base for utility classes used to convert JSONArrays to ArrayLists of the given type.
// (to enable iterating over the objects using a for loop with that type).
abstract class AbstractJSONArrayParser<T> {
    
    public ArrayList<T> parse(JSONArray jsonData) throws JSONException {
        ArrayList<T> output = new ArrayList<>();

        for (int i = 0; i < jsonData.length(); i++) {
            T elem = getElement(jsonData, i);
            output.add(elem);
        }
        return output;
    }

    public ArrayList<T> parse(JSONObject obj, String arrayKey) {
        JSONArray jsonData = obj.getJSONArray(arrayKey);
        return parse(jsonData);
    }

    // Get the value at the given index from the JSON array.
    abstract T getElement(JSONArray jsonData, int index);
}