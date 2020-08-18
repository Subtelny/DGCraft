/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables;


import javax.annotation.processing.Generated;

import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.GuildIslands;
import pl.subtelny.generated.tables.tables.Guilds;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.IslandsMembership;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.generated.tables.tables.SkyblockIslands;


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
     * The table <code>public.accounts</code>.
     */
    public static final Accounts ACCOUNTS = Accounts.ACCOUNTS;

    /**
     * The table <code>public.guild_islands</code>.
     */
    public static final GuildIslands GUILD_ISLANDS = GuildIslands.GUILD_ISLANDS;

    /**
     * The table <code>public.guilds</code>.
     */
    public static final Guilds GUILDS = Guilds.GUILDS;

    /**
     * The table <code>public.islanders</code>.
     */
    public static final Islanders ISLANDERS = Islanders.ISLANDERS;

    /**
     * The table <code>public.islands</code>.
     */
    public static final Islands ISLANDS = Islands.ISLANDS;

    /**
     * The table <code>public.islands_membership</code>.
     */
    public static final IslandsMembership ISLANDS_MEMBERSHIP = IslandsMembership.ISLANDS_MEMBERSHIP;

    /**
     * The table <code>public.login_histories</code>.
     */
    public static final LoginHistories LOGIN_HISTORIES = LoginHistories.LOGIN_HISTORIES;

    /**
     * The table <code>public.skyblock_islands</code>.
     */
    public static final SkyblockIslands SKYBLOCK_ISLANDS = SkyblockIslands.SKYBLOCK_ISLANDS;
}