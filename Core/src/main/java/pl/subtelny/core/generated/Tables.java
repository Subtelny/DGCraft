/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.core.generated;


import javax.annotation.processing.Generated;

import pl.subtelny.core.generated.tables.AnemiaAccount;
import pl.subtelny.core.generated.tables.AnemiaIsland;
import pl.subtelny.core.generated.tables.AnemiaLoginHistory;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.anemia_account</code>.
     */
    public static final AnemiaAccount ANEMIA_ACCOUNT = AnemiaAccount.ANEMIA_ACCOUNT;

    /**
     * The table <code>public.anemia_island</code>.
     */
    public static final AnemiaIsland ANEMIA_ISLAND = AnemiaIsland.ANEMIA_ISLAND;

    /**
     * The table <code>public.anemia_login_history</code>.
     */
    public static final AnemiaLoginHistory ANEMIA_LOGIN_HISTORY = AnemiaLoginHistory.ANEMIA_LOGIN_HISTORY;
}
