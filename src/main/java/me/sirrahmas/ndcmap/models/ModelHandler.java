package me.sirrahmas.ndcmap.models;

import java.text.SimpleDateFormat;

import com.arcadedb.database.Database;
import com.arcadedb.database.DatabaseFactory;
import com.arcadedb.exception.ArcadeDBException;
import com.arcadedb.schema.Schema;

import me.sirrahmas.ndcmap.queries.ExportDataQuery;
import me.sirrahmas.ndcmap.queries.ImportDataQuery;
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
                    new AreaModel().create(s);
                }
            );
        }
    }

    // Migrate the database by exporting the data,
    // deleting the database, recreating it, and re-importing the data.
    // The data will be stored in "/db-exports/migrations/<timestamp>.tgz".
    // If the data cannot be exported or re-imported,
    // ArcadeDBException will be thrown.
    public void migrateDatabase(QueryHandler qh) throws ArcadeDBException {
        // Get URL to export data to.
        final String URL = "file://db-exports/migrations/%s.tgz";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        String url = String.format(URL, dateFormat);

        // Create query objects.
        // The old schema is not included in the export (excludeSchema = true)
        // so that it doesn't override the new one when re-importing the data.
        ExportDataQuery edq = new ExportDataQuery(url, true);
        ImportDataQuery idq = new ImportDataQuery(url);

        // Export data.
        try {qh.doQuery(edq);}
        catch (Exception e) {
            throw new ArcadeDBException(
                """
                Unable to export contents of database `%s`
                to the URL "%s"
                before migrating to a new version.
                The migration did not take place.
                Exception message: "%s"
                """
                .formatted(dbf.getDatabasePath(), url, e.getMessage())
            );
        }

        // Drop database.
        try ( Database db = dbf.open() ) {
            db.drop();
        }

        // Recreate database.
        createDatabase();

        // Import data.
        try {qh.doQuery(idq);}
        catch (Exception e) {
            throw new ArcadeDBException(
                """
                Unable to re-import data to database `%s`
                after migrating to a new version.
                Prior to the migration, the data was successfully
                backed up to the URL "%s".
                Exception message: "%s"
                """
                .formatted(dbf.getDatabasePath(), url, e.getMessage())
            );
        }
    }
}
