package pl.subtelny.core.api.database;

import org.jooq.DSLContext;

@FunctionalInterface
public interface TransactionalCallbackable<T> {

    T call(DSLContext context);

}
