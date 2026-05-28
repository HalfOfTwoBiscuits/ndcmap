package me.sirrahmas.ndcmap.data;

import java.util.ArrayList;

import com.arcadedb.database.Database;
import com.arcadedb.graph.MutableEdge;
import com.arcadedb.graph.MutableVertex;

// Record in the database for a physical, walkable link between two areas.
public class PhysicalEdgeRecord extends AbstractRecord<MutableEdge> {
    private final Integer v1x;
    private final Integer v1y;
    private final Integer v2x;
    private final Integer v2y;
    private final boolean fireExit;
    private final MutableVertex area1;
    private final MutableVertex area2;

    public PhysicalEdgeRecord(MutableVertex fromArea, MutableVertex toArea, ArrayList<Integer> vertex1, ArrayList<Integer> vertex2, boolean isFireExit) {
        area1 = fromArea;
        area2 = toArea;
        v1x = vertex1.get(0);
        v1y = vertex1.get(1);
        v2x = vertex2.get(0);
        v2y = vertex2.get(1);
        fireExit = isFireExit;
    }

    @Override
    MutableEdge createRecord(Database db) {
        return area1.newEdge("Physical", area2);
    }
    
    @Override
    void configure(MutableEdge pEdge) {
        pEdge.set("vertex1X", v1x)
        .set("vertex1Y", v1y)
        .set("vertex2X", v2x)
        .set("vertex2Y", v2y)
        .set("isFireExit", fireExit);
    }
}