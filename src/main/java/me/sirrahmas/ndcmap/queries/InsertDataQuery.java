package me.sirrahmas.ndcmap.queries;

import java.util.ArrayList;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableVertex;
import com.arcadedb.query.sql.executor.ResultSet;

// Query used internally to populate the database.
public class InsertDataQuery extends AbstractQuery {
    @Override
    ResultSet doQuery(Database db) {
        ArrayList cornerXs = new ArrayList<Integer>();
        cornerXs.add(1);
        cornerXs.add(2);

        ArrayList cornerYs = new ArrayList<Integer>();
        cornerYs.add(3);
        cornerYs.add(4);

        MutableVertex dummy = db.newVertex("Area")
        .set("name", "DUMMY")
        .set("cornerXs", cornerXs)
        .set("cornerYs", cornerYs)
        .save();
        return null;
    }
}