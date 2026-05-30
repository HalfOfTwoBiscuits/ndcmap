package me.sirrahmas.ndcmap.queries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableVertex;

import me.sirrahmas.ndcmap.data.AlternateNameRecord;
import me.sirrahmas.ndcmap.data.AreaRecord;
import me.sirrahmas.ndcmap.data.PhysicalEdgeRecord;
import me.sirrahmas.ndcmap.data.TagRecord;
import me.sirrahmas.ndcmap.util.json.CoordinateArrayParser;
import me.sirrahmas.ndcmap.util.json.IntegerArrayParser;
import me.sirrahmas.ndcmap.util.json.JSONFileReader;
import me.sirrahmas.ndcmap.util.json.StringArrayParser;

// Query used internally to populate the database.
// Gets data from JSON files.
public class PopulateDatabaseOperation extends AbstractOperation {
    @Override
    void doOperation(Database db) throws IOException, JSONException {

        JSONFileReader jfr = new JSONFileReader();
        CoordinateArrayParser cap = new CoordinateArrayParser(); 
        IntegerArrayParser iap = new IntegerArrayParser();
        StringArrayParser sap = new StringArrayParser();

        ArrayList<JSONObject> areaData = jfr.readJSON("data/areas.json");
        ArrayList<JSONObject> physicalEdgeData = jfr.readJSON("data/physicalEdges.json");
        ArrayList<JSONObject> tagData = jfr.readJSON("data/tags.json");

        // Store areas by a temporary ID from the JSON,
        // so that edges can be created later.
        HashMap<String, MutableVertex> areas = new HashMap<>();
        HashMap<String, MutableVertex> existingAltNames = new HashMap<>();

        for (JSONObject areaDatum : areaData) {
            // Create areas.
            String areaID = areaDatum.getString("id");
            String areaName = areaDatum.getString("name");

            ArrayList<Integer[]> corners = cap.parse(areaDatum, "corners");
            MutableVertex area = new AreaRecord(areaName, corners).create(db);
            areas.put(areaID, area);

            // Link alternate names.
            ArrayList<String> altNames = sap.parse(areaDatum, "alternateNames");
            for (String altName : altNames) {
                MutableVertex altNameRecord = existingAltNames.get(altName);

                // If the alternate name node doesn't exist, create it.
                if (altNameRecord == null) {
                    altNameRecord = new AlternateNameRecord(altName).create(db);
                    existingAltNames.put(altName, altNameRecord);
                }

                // Join the two.
                area.newEdge("aka", altNameRecord);
            }
        }

        for (JSONObject physicalEdgeDatum : physicalEdgeData) {
            // Create physical edges.
            String areaID1 = physicalEdgeDatum.getString("id1");
            String areaID2 = physicalEdgeDatum.getString("id2");
            ArrayList<Integer> vertex1 = iap.parse(physicalEdgeDatum, "vertex1");
            ArrayList<Integer> vertex2 = iap.parse(physicalEdgeDatum, "vertex2");
            boolean isFireExit = physicalEdgeDatum.getBoolean("isFireExit");

            // Retrieve areas to link.
            MutableVertex area1 = areas.get(areaID1);
            MutableVertex area2 = areas.get(areaID2);
            if (area1 == null | area2 == null) {
                throw new JSONException(
                    "Unable to create edge between areas `" + areaID1 + "` and `" + areaID2 +
                    "`. Area IDs: " + areas.keySet().toString() + " Count: " + areaData.size()
                );
            }
            
            // Create record.
            new PhysicalEdgeRecord(area1, area2, vertex1, vertex2, isFireExit).create(db);
        }

        for (JSONObject tagDatum : tagData) {
            // Create tags.
            String tagName = tagDatum.getString("name");

            // Icon filename may be null.
            String tagIconFilename = null;
            try {
                tagIconFilename = tagDatum.getString("iconFilename");
            }
            catch (JSONException e) {}
            MutableVertex tag = new TagRecord(tagName, tagIconFilename).create(db);

            // Link tags to areas.
            ArrayList<String> taggedAreaIDs = sap.parse(tagDatum, "areasTagged");
            for (String taggedAreaID : taggedAreaIDs) {
                MutableVertex taggedArea = areas.get(taggedAreaID);
                if (taggedArea == null) {
                    throw new JSONException("Cannot apply tag '" + tagName + "' to nonexistent area `" + taggedAreaID + "`");
                }
                taggedArea.newEdge("taggedWith", tag);
            }
        }
    }
}