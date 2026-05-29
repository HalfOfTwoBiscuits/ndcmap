package me.sirrahmas.ndcmap.queries;

import java.util.List;

import com.arcadedb.database.Database;
import com.arcadedb.graph.Vertex;

// Query used to fetch all areas.
public class SelectAllAreasQuery extends AbstractQuery<List<Vertex>> {
    @Override
    List<Vertex> doQuery(Database db) {
        return db.query("SQL", "SELECT * FROM Area").toVertices();
    }
}