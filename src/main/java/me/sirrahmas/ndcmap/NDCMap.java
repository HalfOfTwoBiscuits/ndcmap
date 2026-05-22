package me.sirrahmas.ndcmap;

import com.arcadedb.database.DatabaseFactory;

import io.javalin.Javalin;
import me.sirrahmas.ndcmap.models.ModelHandler;
import me.sirrahmas.ndcmap.queries.QueryHandler;

// Main class responsible for deploying the web app.
public class NDCMap {

    public static void main(String[] args) {
        DatabaseFactory dbf = new DatabaseFactory("databases/ndcmap");
        
        ModelHandler mh = new ModelHandler(dbf);
        QueryHandler qh = new QueryHandler(dbf);
        
        // If the database doesn't exist, create it.
        // If it already exists, migrate it, in case it changed.
        // Later, migration should be made into a separate command.
        if (dbf.exists()) {mh.migrateDatabase(qh);}
        else {mh.createDatabase();}

        // Start web app.
        Controller c = new Controller();
        Javalin j = c.createApp();
        j.start();

        // Next: unit tests!
    }
}


