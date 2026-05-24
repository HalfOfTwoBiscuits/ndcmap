package me.sirrahmas.ndcmap;

import io.javalin.Javalin;
import me.sirrahmas.ndcmap.queries.QueryHandler;
import me.sirrahmas.ndcmap.views.MainPageView;

// Controller class responsible for configuring URL routing for the app.
class Controller {
    public Javalin createApp(QueryHandler qh) {
        return Javalin.create(
            config -> {
                config.routes.get("/", new MainPageView(qh));
            }
        );
    }
}
