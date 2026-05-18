package me.sirrahmas.ndcmap.models;

import com.arcadedb.schema.EdgeType;
import com.arcadedb.schema.Schema;

public class PhysicalEdgeModel extends AbstractModel<EdgeType> {
    @Override
    EdgeType initialiseType(Schema s) {
        return s.createEdgeType("Physical");
    }

    @Override
    ModelProperty[] createProperties() {
        return new ModelProperty[]{
            new ModelProperty("vertex1X", "Integer", null, true),
            new ModelProperty("vertex1Y", "Integer", null, true),
            new ModelProperty("vertex2X", "Integer", null, true),
            new ModelProperty("vertex2Y", "Integer", null, true),
            new ModelProperty("isFireExit", "Boolean", null, true)
        };
    }
}
