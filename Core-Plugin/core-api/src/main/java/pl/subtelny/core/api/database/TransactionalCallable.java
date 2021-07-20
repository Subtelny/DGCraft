package pl.subtelny.core.api.database;

import org.jooq.DSLContext;

@FunctionalInterface
public interface TransactionalCallable {

    void call(DSLContext context);

}
