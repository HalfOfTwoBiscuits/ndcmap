package me.sirrahmas.ndcmap.models;

// DocumentType is the base interface for vertex and edge types.
import com.arcadedb.schema.DocumentType;
import com.arcadedb.schema.Property;

// Property of a type of record in the database.
// Constructed in the model classes for said record types.
class ModelProperty {
    private final String name;
    private final String dTypeName;
    private final String vTypeName;
    private final boolean nNull;

    public ModelProperty (String propertyName, String dataTypeName, String valueTypeName, boolean notNull) {
        name = propertyName;
        dTypeName = dataTypeName;
        vTypeName = valueTypeName;
        nNull = notNull;
    }

    public Property addTo(DocumentType type) {
        Property p = type.createProperty(name, dTypeName);
        p.setOfType(vTypeName);
        p.setNotNull(nNull);
        return p;
    }
}