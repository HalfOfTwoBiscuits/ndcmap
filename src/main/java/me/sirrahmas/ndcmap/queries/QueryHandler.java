package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.BasicDatabase.TransactionScope;
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

    public ResultSet doQuery(AbstractQuery q) {
        try (Database db = dbf.open();) {
            TransactionScope t = q.getTransactionScope(db);
            db.transaction(t);
            return q.getResults();
        }
    }
}