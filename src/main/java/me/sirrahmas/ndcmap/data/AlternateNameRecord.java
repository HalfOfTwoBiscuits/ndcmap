package me.sirrahmas.ndcmap.data;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableVertex;

// Record in the database for an alternate name used as a search criteria.
public class AlternateNameRecord extends AbstractRecord<MutableVertex> {
    private final String name;

    public AlternateNameRecord(String altName) {
        name = altName;
    }

    @Override
    MutableVertex createRecord(Database db) {
        return db.newVertex("AlternateName");
    }
    
    @Override
    void configure(MutableVertex altName) {
        altName.set("name", name);
    }
}