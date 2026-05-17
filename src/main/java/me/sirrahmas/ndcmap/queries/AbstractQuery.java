package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.BasicDatabase.TransactionScope;
import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.ResultSet;

// Query executed by the web app at runtime.
// This abstract class provides a public method that returns a lambda
// for `me.sirrahmas.ndcmap.QueryHandler` to use to execute the query.
// Children should specify the contents of the query in the `doQuery` method.
public abstract class AbstractQuery {
    private ResultSet results;
    ResultSet getResults() {return results;}

    // Contents of the query.
    // If it throws an exception, the transaction will be rolled back
    // and the exception will be thrown again.
    // It may return a ResultSet to the caller, or return null.
    abstract ResultSet doQuery(Database db);

    // Return a lambda that passes the provided database object
    // to the doQuery method, storing the result for later retrieval.
    // The lambda retains the database object,
    // so is capable of executing the query without any arguments,
    // making it possible for `me.sirrahmas.ndcmap.QueryHandler`
    // to use it in a transaction context.
    // As in the ArcadeDB code, this lambda is referred to as
    // the transaction scope.
    TransactionScope getTransactionScope(Database db) {
        return () -> {results = doQuery(db);};
    }
}