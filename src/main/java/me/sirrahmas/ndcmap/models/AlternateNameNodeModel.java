package me.sirrahmas.ndcmap.models;

import com.arcadedb.schema.VertexType;
import com.arcadedb.schema.Schema;

public class AlternateNameNodeModel extends AbstractModel<VertexType> {
    @Override
    VertexType initialiseType(Schema s) {
        return s.createVertexType("AlternateName");
    }

    @Override
    ModelProperty[] createProperties() {
        return new ModelProperty[]{
            new ModelProperty("name", "String", null, true),
            new ModelProperty("iconFilename", "String", null, true),
        };
    }
}
