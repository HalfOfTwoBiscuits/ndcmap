package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.ResultSet;

// Query used internally to export data from the database.
public class ExportDataQuery extends AbstractQuery {
    final private String url;
    final private boolean noSchema;

    public ExportDataQuery(String fileUrl, boolean excludeSchema) {
        url = fileUrl;
        noSchema = excludeSchema;
    }

    @Override
    ResultSet doQuery(Database db) {
        final String QUERY = "EXPORT DATABASE %s WITH excludeTypes = %b";
        String query = String.format(QUERY, url, noSchema);
        return db.command("SQL", query);
    }
}