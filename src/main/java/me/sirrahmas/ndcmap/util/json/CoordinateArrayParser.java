package me.sirrahmas.ndcmap.util.json;

import org.json.JSONArray;

// Utility class that reads an array of 2D co-ordinates from a JSON file.
public class CoordinateArrayParser extends AbstractJSONArrayParser<Integer[]> {

    @Override
    Integer[] getElement(JSONArray jsonData, int index) {
        JSONArray coordinate = jsonData.getJSONArray(index);
        return new Integer[] {
            coordinate.getInt(0),
            coordinate.getInt(1)
        };
    };
}