package me.sirrahmas.ndcmap.views;

import io.javalin.http.Context;
import io.javalin.http.Handler;

// Main page, containing the map widget.
public class MainPageView implements Handler {
    @Override
    public void handle(Context ctx) {
        ctx.render("/templates/index.html");
    };
}