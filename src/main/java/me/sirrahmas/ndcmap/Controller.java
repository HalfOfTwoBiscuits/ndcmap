package me.sirrahmas.ndcmap;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinFreemarker;
import me.sirrahmas.ndcmap.queries.QueryHandler;
import me.sirrahmas.ndcmap.views.FetchAreaView;
import me.sirrahmas.ndcmap.views.MainPageView;

// Controller class responsible for configuring URL routing for the app.
class Controller {
    public Javalin createApp(QueryHandler qh) {
        return Javalin.create(
            config -> {
                config.staticFiles.add("/static");
                config.fileRenderer(new JavalinFreemarker());
                config.routes.get("/", new MainPageView(qh));
                config.routes.get("/getAreaFromPos/{x}/{y}", new FetchAreaView(qh));
            }
        );
    }
}
