package me.sirrahmas.ndcmap.data;

import java.util.ArrayList;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableVertex;

// Record in the database for an area on the map.
public class AreaRecord extends AbstractRecord<MutableVertex> {
    private final String name;
    private final ArrayList<Integer> cornerXs = new ArrayList<>();
    private final ArrayList<Integer> cornerYs = new ArrayList<>();
    private final Integer centroidX;
    private final Integer centroidY;

    public AreaRecord(String areaName, ArrayList<Integer[]> corners) {
        name = areaName;

        int totalX = 0;
        int totalY = 0;
        int cornerCount = cornerXs.size();

        for (Integer[] corner : corners) {
            Integer x = corner[0];
            Integer y = corner[1];

            // Sum x and y co-ordinates to calculate approximate centre.
            totalX = totalX + x;
            totalY = totalY + y;

            cornerXs.add(x);
            cornerYs.add(y);
        }
        centroidX = totalX / cornerCount;
        centroidY = totalY / cornerCount;
    }

    @Override
    MutableVertex createRecord(Database db) {
        return db.newVertex("Area");
    }
    
    @Override
    void configure(MutableVertex area) {
        area.set("name", name)
        .set("cornerXs", cornerXs)
        .set("cornerYs", cornerYs)
        .set("centroidX", centroidX)
        .set("centroidY", centroidY);
    }
}