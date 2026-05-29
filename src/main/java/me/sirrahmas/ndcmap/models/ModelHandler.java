package me.sirrahmas.ndcmap.models;

import com.arcadedb.database.Database;
import com.arcadedb.database.DatabaseFactory;
import com.arcadedb.schema.Schema;

import me.sirrahmas.ndcmap.queries.PopulateDatabaseOperation;
import me.sirrahmas.ndcmap.queries.QueryHandler;

// Class responsible for applying the schema to the database
// using the model classes from `me.sirrahmas.ndcmap.models`.
// It is capable of creating the database, and migrating it when the models change.
public class ModelHandler {
    private final DatabaseFactory dbf;

    public ModelHandler(DatabaseFactory databaseFactory) {
        dbf = databaseFactory;
    }
    
    // Create the database according to the schema
    // provided by the models in `me.sirrahmas.ndcmap.models`.
    public void createDatabase() {
        try ( Database db = dbf.create(); ) {
            db.transaction(
                () -> {
                    Schema s = db.getSchema();
                    new AreaNodeModel().create(s);
                    new TagNodeModel().create(s);
                    new AlternateNameNodeModel().create(s);
                    new PhysicalEdgeModel().create(s);

                    // Simple types of edge with no properties
                    s.createEdgeType("aka");
                    s.createEdgeType("taggedWith");
                }
            );
        }
    }

    // Migrate the database by deleting it, recreating it, and re-inserting the data.
    // The data will be stored in "exports/ndcmap-export-<timestamp>.jsonl.tgz".
    // If the export fails, then the migration will not take place -
    // if the import fails, then that's where the data will be backed up.
    public void migrateDatabase(QueryHandler qh) throws Exception {

        // Query to populate database.
        PopulateDatabaseOperation pdo = new PopulateDatabaseOperation();

        // Drop database.
        try ( Database db = dbf.open() ) {
            db.drop();
        }

        // Recreate database.
        createDatabase();

        // Re-insert data.
        qh.doOperation(pdo);
    }
}
