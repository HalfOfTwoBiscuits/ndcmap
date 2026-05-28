package me.sirrahmas.ndcmap.data;

import java.util.ArrayList;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableVertex;

// Record in the database for an area on the map.
public class AreaRecord extends AbstractRecord<MutableVertex> {
    private final String name;
    private final ArrayList<Integer> cornerXs = new ArrayList<>();
    private final ArrayList<Integer> cornerYs = new ArrayList<>();

    public AreaRecord(String areaName, ArrayList<Integer[]> corners) {
        name = areaName;
        for (Integer[] corner : corners) {
            Integer x = corner[0];
            Integer y = corner[1];
            cornerXs.add(x);
            cornerYs.add(y);
        }
    }

    @Override
    MutableVertex createRecord(Database db) {
        return db.newVertex("Area");
    }
    
    @Override
    void configure(MutableVertex area) {
        area.set("name", name)
        .set("cornerXs", cornerXs)
        .set("cornerYs", cornerYs);
    }
}