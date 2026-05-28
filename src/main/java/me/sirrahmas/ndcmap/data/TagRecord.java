package me.sirrahmas.ndcmap.data;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableVertex;

// Record in the database for a tag given to areas to describe their type.
public class TagRecord extends AbstractRecord<MutableVertex> {
    private final String name;
    private final String filename;

    public TagRecord(String tagName, String iconFilename) {
        name = tagName;
        filename = iconFilename;
    }

    @Override
    MutableVertex createRecord(Database db) {
        return db.newVertex("Tag");
    }
    
    @Override
    void configure(MutableVertex tag) {
        tag.set("name", name)
        .set("iconFilename", filename);
    }
}