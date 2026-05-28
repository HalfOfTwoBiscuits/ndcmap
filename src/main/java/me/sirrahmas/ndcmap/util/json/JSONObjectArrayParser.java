package me.sirrahmas.ndcmap.util.json;

import org.json.JSONArray;
import org.json.JSONObject;

// Utility class that reads an array of JSONObjects from a json file.
public class JSONObjectArrayParser extends AbstractJSONArrayParser<JSONObject> {

    @Override
    JSONObject getElement(JSONArray jsonData, int index) {
        return jsonData.getJSONObject(index);
    };
}