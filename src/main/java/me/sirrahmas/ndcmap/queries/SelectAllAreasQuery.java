package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.ResultSet;

// Query used to fetch all areas.
public class SelectAllAreasQuery extends AbstractQuery {
    @Override
    ResultSet doQuery(Database db) {
        return db.query("SQL", "SELECT * FROM Area");
    }
}