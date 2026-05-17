package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.ResultSet;

// Query used internally to import data to the database.
public class ImportDataQuery extends AbstractQuery {
    final private String url;

    public ImportDataQuery(String fileUrl) {
        url = fileUrl;
    }

    @Override
    ResultSet doQuery(Database db) {
        return db.command("SQL", "IMPORT DATABASE " + url);
    }
}