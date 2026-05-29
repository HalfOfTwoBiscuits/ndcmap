package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;

// Database operation to be executed by the app at runtime.
// Children should specify its contents in the execute() method.
// T is the return type for the query.
public abstract class AbstractOperation implements DatabaseInteraction {

    // Contents of the operation.
    // If it throws an exception, the transaction will be rolled back
    // and the exception will be thrown again.
    abstract void doOperation(Database db) throws Exception;

    @Override
    public void execute(Database db) throws DatabaseInteractionException {
        try {
            doOperation(db);
        }
        catch (Exception e) {
            throw new DatabaseInteractionException("Exception during database operation", e);
        }
    }
}