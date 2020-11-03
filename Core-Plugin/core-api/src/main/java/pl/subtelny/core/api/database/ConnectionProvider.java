package pl.subtelny.core.api.database;

import org.jooq.DSLContext;

public interface ConnectionProvider {

    DSLContext getCurrentConnection();

}
