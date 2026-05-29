package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;
import com.arcadedb.database.DatabaseFactory;
import com.arcadedb.query.sql.executor.ResultSet;

// Class responsible for executing database queries.
// The queries are represented by implementations of 
// `me.sirrahmas.ndcmap.queries.AbstractQuery`.
public class QueryHandler {

    private final DatabaseFactory dbf;

    public QueryHandler(DatabaseFactory databaseFactory) {
        dbf = databaseFactory;
    }

    // Executes the query. Commits on success, on failure, rolls back and throws the exception again.
    // Returns either the query result, or null for no result.
    // NOTE: In a future project, maybe a better name than 'query' would be 'operation'?
    public ResultSet doQuery(AbstractQuery q) throws QueryException {
        try (Database db = dbf.open();) {
            try {
                db.begin();
                q.execute(db);
                db.commit();
            } catch (Exception e) {
                db.rollback();
                throw e;
            } finally {
                db.close();
            }
            return q.getResults();
        }
    }
}
