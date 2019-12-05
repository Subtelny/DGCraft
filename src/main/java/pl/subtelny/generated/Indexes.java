/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated;


import javax.annotation.processing.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

import pl.subtelny.generated.tables.EntityAccount;
import pl.subtelny.generated.tables.EntityLoginHistory;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ENTITY_ACCOUNT_PKEY = Indexes0.ENTITY_ACCOUNT_PKEY;
    public static final Index ENTITY_LOGIN_HISTORY_PKEY = Indexes0.ENTITY_LOGIN_HISTORY_PKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ENTITY_ACCOUNT_PKEY = Internal.createIndex("entity_account_pkey", EntityAccount.ENTITY_ACCOUNT, new OrderField[] { EntityAccount.ENTITY_ACCOUNT.UUID }, true);
        public static Index ENTITY_LOGIN_HISTORY_PKEY = Internal.createIndex("entity_login_history_pkey", EntityLoginHistory.ENTITY_LOGIN_HISTORY, new OrderField[] { EntityLoginHistory.ENTITY_LOGIN_HISTORY.ENTITY_ACCOUNT }, true);
    }
}
