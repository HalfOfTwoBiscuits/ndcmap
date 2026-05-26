package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.ResultSet;

import me.sirrahmas.ndcmap.data.CollegeEntranceArea;

// Query used internally to populate the database.
public class InsertDataQuery extends AbstractQuery {
    @Override
    ResultSet doQuery(Database db) {
        new CollegeEntranceArea().create(db);
        return null;
    }
}