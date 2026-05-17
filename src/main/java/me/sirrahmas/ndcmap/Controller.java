package me.sirrahmas.ndcmap;

import io.javalin.Javalin;

// Controller class responsible for configuring URL routing for the app.
class Controller {
    public Javalin createApp() {
        return Javalin.create(
            config -> config.routes.get("/", ctx -> ctx.result("Hello World"))
        );
    }
}
