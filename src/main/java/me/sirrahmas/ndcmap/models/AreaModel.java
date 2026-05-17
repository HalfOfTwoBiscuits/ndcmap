package me.sirrahmas.ndcmap.models;

import com.arcadedb.schema.Schema;
import com.arcadedb.schema.VertexType;

class AreaModel extends AbstractModel<VertexType> {
    @Override
    VertexType initialiseType(Schema s) {
        return s.createVertexType("Area");
    }

    @Override
    ModelProperty[] createProperties() {
        return new ModelProperty[]{
            new ModelProperty("name", "String", null, true),
            new ModelProperty("cornerXs", "List", "Integer", true),
            new ModelProperty("cornerYs", "List", "Integer", true)
        };
    }
}