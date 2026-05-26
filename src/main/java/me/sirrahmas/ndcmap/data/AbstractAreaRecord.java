package me.sirrahmas.ndcmap.data;

import java.util.Arrays;
import java.util.List;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableVertex;

// Record in the database for an area on the map.
abstract class AbstractAreaRecord extends AbstractRecord<MutableVertex> {
    private final String name;
    private final List<Integer> cornerXs;
    private final List<Integer> cornerYs;

    AbstractAreaRecord(String areaName, Integer[] areaCornerXs, Integer[] areaCornerYs) {
        name = areaName;
        cornerXs = Arrays.asList(areaCornerXs); 
        cornerYs = Arrays.asList(areaCornerYs);
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