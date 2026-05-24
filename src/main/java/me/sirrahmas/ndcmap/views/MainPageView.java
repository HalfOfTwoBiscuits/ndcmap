package me.sirrahmas.ndcmap.views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import me.sirrahmas.ndcmap.queries.QueryHandler;

// Main page, containing the map widget.
public class MainPageView implements Handler {
    private final QueryHandler qh;

    public MainPageView(QueryHandler queryHandler) {qh = queryHandler;}

    @Override
    public void handle(Context ctx) {
        ctx.result("Hello World");
    };
}