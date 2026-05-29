package me.sirrahmas.ndcmap.queries;

import com.arcadedb.database.Database;

// Base interface for database operations and queries.
public interface DatabaseInteraction {
    void execute(Database db) throws DatabaseInteractionException;
}