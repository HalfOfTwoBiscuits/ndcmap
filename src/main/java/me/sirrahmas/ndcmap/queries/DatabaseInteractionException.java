package me.sirrahmas.ndcmap.queries;

// Exception thrown by a failed database query or operation.
public class DatabaseInteractionException extends Exception {
    public DatabaseInteractionException(String message, Throwable cause) {
        super(message, cause);
    }
};