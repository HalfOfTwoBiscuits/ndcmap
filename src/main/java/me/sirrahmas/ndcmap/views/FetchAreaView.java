package me.sirrahmas.ndcmap.views;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.util.JavalinLogger;
import me.sirrahmas.ndcmap.queries.AreaResult;
import me.sirrahmas.ndcmap.queries.DatabaseInteractionException;
import me.sirrahmas.ndcmap.queries.QueryHandler;
import me.sirrahmas.ndcmap.queries.SelectAllAreasQuery;

// Endpoint to fetch data for the area matching the tapped x,y position on-map.
public class FetchAreaView implements Handler {
    private final QueryHandler qh;

    public FetchAreaView (QueryHandler queryHandler) {qh = queryHandler;}

    private record JSONResponse(
        String areaName,
        Integer centroidX, Integer centroidY,
        List<String> altNames
    ) {}

    @Override
    public void handle(Context ctx) throws DatabaseInteractionException, NumberFormatException {
        Integer x;
        Integer y;
        try {
            x = Integer.valueOf(ctx.pathParam("x"));
            y = Integer.valueOf(ctx.pathParam("y"));
        } catch (NumberFormatException e) {
            // If x,y integers aren't provided, status code 400 (bad request).
            ctx.status(400);
            return;
        }

        AreaResult areaTapped = getAreaTapped(x,y);
        if (areaTapped == null) {
            // If the tapped location isn't in an area, status code 404 (not found).
            ctx.status(404);
            return;
        }
        
        // Return JSON data.
        JSONResponse r = new JSONResponse(
            areaTapped.name(),
            areaTapped.centroidX(),
            areaTapped.centroidY(),
            areaTapped.altNames()
        );
        ctx.json(r);
    };

    // Get area tapped, or null if none.
    AreaResult getAreaTapped(Integer x, Integer y) throws DatabaseInteractionException {
        JavalinLogger.info("Tapped area at " + x + ", " + y);

        SelectAllAreasQuery saaq = new SelectAllAreasQuery();
        List<AreaResult> areas = qh.doQuery(saaq);

        JavalinLogger.info("Found " + areas.size() + "vertices");

        for (AreaResult area : areas) {
            JavalinLogger.info("Checking area: " + area.name());
            if (positionIsWithinArea(area, x, y)) {
                JavalinLogger.info("Within area");
                return area;
            }
            else {
                JavalinLogger.info("Not within area");
            }
        }

        return null;
    }

    // Return boolean for whether the position is within the area.
    boolean positionIsWithinArea(AreaResult area, Integer touchedX, Integer touchedY) {
        List<Integer> cornerXs = area.cornerXs();
        List<Integer> cornerYs = area.cornerYs();

        int numCorners = cornerXs.size();
        boolean result = false;

        JavalinLogger.info("Area: `" + area.name() + "` has " + numCorners + " corners");
        // Iterate through corners in pairs.
        // Start with the first and last corner.
        int index2 = numCorners - 1;

        for (int index1 = 0; index1 < numCorners; index1++) {
            Integer corner1x = cornerXs.get(index1);
            Integer corner1y = cornerYs.get(index1);
            
            Integer corner2x = cornerXs.get(index2);
            Integer corner2y = cornerYs.get(index2);

            if (
                // If the side between the two corners goes from
                // above the touched point to below it,
                (corner1y > touchedY) != (corner2y > touchedY) 
                &&
                // and a horizontal line from the touched point would cross that side,
                touchedX < (corner2x - corner1x) * (touchedY - corner1y) / (corner2y - corner1y) + corner1x
            ) {
                JavalinLogger.info("Passed through the edge of the shape.");
                // then the line has exited the shape, meaning the point must be inside.
                result = !result;
                // or, if this occurs a second time, the line has entered the shape again
                // meaning the point must be fully to one side of the shape.
                // (for a strangely-shaped room, e.g. a U shape, it could even enter and exit again
                // and the algorithm would still work)
            }

            // By setting index2 to the current value of index1 before incrementing,
            // index2 is the element before index1.
            // This means, by the time index1 reaches the end and the loop terminates,
            // all sides have been parsed.
            index2 = index1;
        }
        return result;
    }
}