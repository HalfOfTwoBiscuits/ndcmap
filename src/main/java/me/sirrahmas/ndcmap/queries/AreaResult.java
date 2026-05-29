package me.sirrahmas.ndcmap.queries;

import java.util.ArrayList;
import java.util.List;

import com.arcadedb.graph.Vertex;
import com.arcadedb.graph.Vertex.DIRECTION;

// Record used to return area nodes from queries.
// In retrospect, a record like this would have been a better way to load in the JSON when populating the database,
// rather than bundling that with the logic to insert the data in the `me.sirrahmas.ndcmap.data` classes.
public record AreaResult(String name, List<Integer> cornerXs, List<Integer> cornerYs, Integer centroidX, Integer centroidY, List<String> altNames) {

    public AreaResult(Vertex v) {

        // Traverse alternate name nodes to get a list of alt names.
        List<Vertex> altNameNodes = v.getVertices(DIRECTION.BOTH, "aka").toList();
        List<String> altNameStrings = new ArrayList<>();

        for (Vertex altNameNode : altNameNodes) {
            altNameStrings.add(altNameNode.getString("name"));
        }

        this(
            v.getString("name"),
            v.getList("cornerXs"),
            v.getList("cornerYs"),
            v.getInteger("centroidX"),
            v.getInteger("centroidY"),
            altNameStrings
        );
    }
}