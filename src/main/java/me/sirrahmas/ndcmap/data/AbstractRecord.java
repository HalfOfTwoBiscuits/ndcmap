package me.sirrahmas.ndcmap.data;

import com.arcadedb.database.Database;
import com.arcadedb.database.MutableDocument;

// Class representing a record to populate the database with.
abstract class AbstractRecord<T extends MutableDocument> {

    public T create(Database db) {
        T record = createRecord(db);
        configure(record);
        record.save();
        return record;
    };

    // Create the record.
    abstract T createRecord(Database db);

    // Configure its properties.
    abstract void configure(T r);
}