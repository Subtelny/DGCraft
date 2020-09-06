/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables;


import javax.annotation.processing.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.generated.tables.tables.SkyblockIslands;


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

    public static final Index ACCOUNTS_ID_PK = Indexes0.ACCOUNTS_ID_PK;
    public static final Index ACCOUNTS_NAME_UQ = Indexes0.ACCOUNTS_NAME_UQ;
    public static final Index ISLAND_MEMBERSHIPS_UQ = Indexes0.ISLAND_MEMBERSHIPS_UQ;
    public static final Index ISLANDERS_ID_PK = Indexes0.ISLANDERS_ID_PK;
    public static final Index ISLANDS_ID_PK = Indexes0.ISLANDS_ID_PK;
    public static final Index LH_ACCOUNT = Indexes0.LH_ACCOUNT;
    public static final Index SKYBLOCK_ISLAND_X_Z_PK = Indexes0.SKYBLOCK_ISLAND_X_Z_PK;
    public static final Index SKYBLOCK_ISLANDS_ID_PK = Indexes0.SKYBLOCK_ISLANDS_ID_PK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ACCOUNTS_ID_PK = Internal.createIndex("accounts_id_pk", Accounts.ACCOUNTS, new OrderField[] { Accounts.ACCOUNTS.ID }, true);
        public static Index ACCOUNTS_NAME_UQ = Internal.createIndex("accounts_name_uq", Accounts.ACCOUNTS, new OrderField[] { Accounts.ACCOUNTS.NAME }, true);
        public static Index ISLAND_MEMBERSHIPS_UQ = Internal.createIndex("island_memberships_uq", IslandMemberships.ISLAND_MEMBERSHIPS, new OrderField[] { IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID, IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID }, true);
        public static Index ISLANDERS_ID_PK = Internal.createIndex("islanders_id_pk", Islanders.ISLANDERS, new OrderField[] { Islanders.ISLANDERS.ID }, true);
        public static Index ISLANDS_ID_PK = Internal.createIndex("islands_id_pk", Islands.ISLANDS, new OrderField[] { Islands.ISLANDS.ID }, true);
        public static Index LH_ACCOUNT = Internal.createIndex("lh_account", LoginHistories.LOGIN_HISTORIES, new OrderField[] { LoginHistories.LOGIN_HISTORIES.ACCOUNT }, true);
        public static Index SKYBLOCK_ISLAND_X_Z_PK = Internal.createIndex("skyblock_island_x_z_pk", SkyblockIslands.SKYBLOCK_ISLANDS, new OrderField[] { SkyblockIslands.SKYBLOCK_ISLANDS.X, SkyblockIslands.SKYBLOCK_ISLANDS.Z }, true);
        public static Index SKYBLOCK_ISLANDS_ID_PK = Internal.createIndex("skyblock_islands_id_pk", SkyblockIslands.SKYBLOCK_ISLANDS, new OrderField[] { SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID }, true);
    }
}
