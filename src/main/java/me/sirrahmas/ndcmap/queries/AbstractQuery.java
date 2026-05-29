package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;

// Query to be executed by the web app at runtime.
// Children should specify its contents in the execute() method.
// It is like an AbstractOperation but it has a return value of type T.
public abstract class AbstractQuery<T> implements DatabaseInteraction {
    private T results;
    T getResults() {return results;}

    // Contents of the query.
    // If it throws an exception, the transaction will be rolled back
    // and the exception will be thrown again.
    // It returns a result of type T to the caller.
    abstract T doQuery(Database db) throws Exception;

    @Override
    public void execute(Database db) throws DatabaseInteractionException {
        try {
            results = doQuery(db);
        }
        catch (Exception e) {
            throw new DatabaseInteractionException("Exception during database query", e);
        }
    }
}