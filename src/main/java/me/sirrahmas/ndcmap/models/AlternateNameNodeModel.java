package me.sirrahmas.ndcmap.models;

import com.arcadedb.schema.Schema;
import com.arcadedb.schema.VertexType;

public class AlternateNameNodeModel extends AbstractModel<VertexType> {
    @Override
    VertexType initialiseType(Schema s) {
        return s.createVertexType("AlternateName");
    }

    @Override
    ModelProperty[] createProperties() {
        return new ModelProperty[]{
            new ModelProperty("content", "String", null, true),
            new ModelProperty("iconFilename", "String", null, true),
        };
    }
}
