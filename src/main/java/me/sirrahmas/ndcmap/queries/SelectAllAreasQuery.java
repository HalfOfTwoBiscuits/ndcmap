package me.sirrahmas.ndcmap.queries;

import java.util.ArrayList;
import java.util.List;

import com.arcadedb.database.Database;
import com.arcadedb.graph.Vertex;

// Query used to fetch all areas.
public class SelectAllAreasQuery extends AbstractQuery<List<AreaResult>> {
    @Override
    List<AreaResult> doQuery(Database db) {
        List<Vertex> areas = db.query("SQL", "SELECT FROM Area").toVertices();
        List<AreaResult> results = new ArrayList<>();
        for (Vertex area : areas) {
            results.add(
                new AreaResult(area)
            );
        }
        return results;
    }
}