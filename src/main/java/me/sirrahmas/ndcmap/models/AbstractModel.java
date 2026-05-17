package me.sirrahmas.ndcmap.models;

// DocumentType is the base interface for vertex and edge types.
import com.arcadedb.schema.DocumentType;
import com.arcadedb.schema.Schema;

// Model for a type of record in the database.
// This abstract class contains the public method to construct and return the type.
// A child implementation will specify the type's name and properties.
// The type's class, such as vertex or edge, is provided as a generic.
abstract class AbstractModel<T extends DocumentType> {

    // Create type, add it to the schema, and return it.
    abstract T initialiseType(Schema s);

    // Create and return an array of ModelProperty objects
    // representing the properties of this model.
    abstract ModelProperty[] createProperties();

    // Create record type, add it to the schema,
    // give it properties, and return it.
    // The type's name, class, and properties
    // are specified by the child's initialiseType
    // and createProperties implementations.
    public T create(Schema s) {
        T type = initialiseType(s);
        ModelProperty[] properties = createProperties();

        for (ModelProperty p : properties) {
            p.addTo(type);
        }
        return type;
    }
}