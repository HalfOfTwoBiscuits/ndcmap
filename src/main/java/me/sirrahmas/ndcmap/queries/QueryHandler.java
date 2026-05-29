package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;
import com.arcadedb.database.DatabaseFactory;

// Class responsible for executing database queries.
// The queries are represented by implementations of 
// `me.sirrahmas.ndcmap.queries.AbstractQuery`.
public class QueryHandler {

    private final DatabaseFactory dbf;

    public QueryHandler(DatabaseFactory databaseFactory) {
        dbf = databaseFactory;
    }

    // Executes the query. Commits on success, on failure, rolls back and throws the exception again.
    // Returns the query result.
    public <T> T doQuery(AbstractQuery<T> q) throws DatabaseInteractionException {
        doTransaction(q);
        return q.getResults();
    }

    // Executes the operation. Like doQuery but doesn't return a result.
    public void doOperation(AbstractOperation o) throws DatabaseInteractionException {
        doTransaction(o);
    }

    private void doTransaction(DatabaseInteraction i) throws DatabaseInteractionException {
        try (Database db = dbf.open();) {
            try {
                db.begin();
                i.execute(db);
                db.commit();
            } catch (DatabaseInteractionException e) {
                db.rollback();
                throw e;
            } finally {
                db.close();
            }
        }
    }
}
