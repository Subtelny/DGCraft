/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.islands.generated.enums;


import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

import pl.subtelny.islands.generated.Public;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum Islandmembertype implements EnumType {

    ISLANDER("ISLANDER"),

    GUILD("GUILD");

    private final String literal;

    private Islandmembertype(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema() == null ? null : getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public String getName() {
        return "islandmembertype";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
