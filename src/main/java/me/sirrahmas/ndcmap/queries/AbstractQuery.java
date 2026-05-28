package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.ResultSet;

// Query executed by the web app at runtime.
// This abstract class provides a method that returns a lambda
// for `me.sirrahmas.ndcmap.queries.QueryHandler` to use to execute the query.
// Children should specify the contents of the query in the `doQuery` method.
public abstract class AbstractQuery {
    private ResultSet results;
    ResultSet getResults() {return results;}

    // Contents of the query.
    // If it throws an exception, the transaction will be rolled back
    // and the exception will be thrown again.
    // It may return a ResultSet to the caller, or return null.
    abstract ResultSet doQuery(Database db) throws Exception;

    void execute(Database db) throws Exception {
        results = doQuery(db);
    }
}