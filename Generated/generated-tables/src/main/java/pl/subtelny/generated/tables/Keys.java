/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables;


import javax.annotation.processing.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.GuildIslands;
import pl.subtelny.generated.tables.tables.Guilds;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.IslandsMembership;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.generated.tables.tables.records.AccountsRecord;
import pl.subtelny.generated.tables.tables.records.GuildIslandsRecord;
import pl.subtelny.generated.tables.tables.records.GuildsRecord;
import pl.subtelny.generated.tables.tables.records.IslandersRecord;
import pl.subtelny.generated.tables.tables.records.IslandsMembershipRecord;
import pl.subtelny.generated.tables.tables.records.IslandsRecord;
import pl.subtelny.generated.tables.tables.records.LoginHistoriesRecord;
import pl.subtelny.generated.tables.tables.records.SkyblockIslandsRecord;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<GuildsRecord, Integer> IDENTITY_GUILDS = Identities0.IDENTITY_GUILDS;
    public static final Identity<IslandsRecord, Integer> IDENTITY_ISLANDS = Identities0.IDENTITY_ISLANDS;
    public static final Identity<LoginHistoriesRecord, Integer> IDENTITY_LOGIN_HISTORIES = Identities0.IDENTITY_LOGIN_HISTORIES;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountsRecord> ACCOUNTS_PKEY = UniqueKeys0.ACCOUNTS_PKEY;
    public static final UniqueKey<GuildIslandsRecord> GUILD_ISLANDS_PKEY = UniqueKeys0.GUILD_ISLANDS_PKEY;
    public static final UniqueKey<GuildsRecord> GUILDS_PKEY = UniqueKeys0.GUILDS_PKEY;
    public static final UniqueKey<IslandersRecord> ISLANDERS_PKEY = UniqueKeys0.ISLANDERS_PKEY;
    public static final UniqueKey<IslandsRecord> ISLANDS_PKEY = UniqueKeys0.ISLANDS_PKEY;
    public static final UniqueKey<IslandsMembershipRecord> ISLANDS_MEMBERSHIP_PKEY = UniqueKeys0.ISLANDS_MEMBERSHIP_PKEY;
    public static final UniqueKey<LoginHistoriesRecord> LOGIN_HISTORIES_PKEY = UniqueKeys0.LOGIN_HISTORIES_PKEY;
    public static final UniqueKey<SkyblockIslandsRecord> SKYBLOCK_ISLANDS_PKEY = UniqueKeys0.SKYBLOCK_ISLANDS_PKEY;
    public static final UniqueKey<SkyblockIslandsRecord> SKYBLOCK_ISLANDS_X_Z_KEY = UniqueKeys0.SKYBLOCK_ISLANDS_X_Z_KEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<GuildIslandsRecord, IslandsRecord> GUILD_ISLANDS__GUILD_ISLANDS_ISLAND_ID_FKEY = ForeignKeys0.GUILD_ISLANDS__GUILD_ISLANDS_ISLAND_ID_FKEY;
    public static final ForeignKey<IslandersRecord, AccountsRecord> ISLANDERS__ISLANDERS_ID_FKEY = ForeignKeys0.ISLANDERS__ISLANDERS_ID_FKEY;
    public static final ForeignKey<IslandsMembershipRecord, IslandersRecord> ISLANDS_MEMBERSHIP__ISLANDS_MEMBERSHIP_ISLANDER_ID_FKEY = ForeignKeys0.ISLANDS_MEMBERSHIP__ISLANDS_MEMBERSHIP_ISLANDER_ID_FKEY;
    public static final ForeignKey<IslandsMembershipRecord, IslandsRecord> ISLANDS_MEMBERSHIP__ISLANDS_MEMBERSHIP_ISLAND_ID_FKEY = ForeignKeys0.ISLANDS_MEMBERSHIP__ISLANDS_MEMBERSHIP_ISLAND_ID_FKEY;
    public static final ForeignKey<LoginHistoriesRecord, AccountsRecord> LOGIN_HISTORIES__LOGIN_HISTORIES_ACCOUNT_FKEY = ForeignKeys0.LOGIN_HISTORIES__LOGIN_HISTORIES_ACCOUNT_FKEY;
    public static final ForeignKey<SkyblockIslandsRecord, IslandsRecord> SKYBLOCK_ISLANDS__SKYBLOCK_ISLANDS_ISLAND_ID_FKEY = ForeignKeys0.SKYBLOCK_ISLANDS__SKYBLOCK_ISLANDS_ISLAND_ID_FKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<GuildsRecord, Integer> IDENTITY_GUILDS = Internal.createIdentity(Guilds.GUILDS, Guilds.GUILDS.ID);
        public static Identity<IslandsRecord, Integer> IDENTITY_ISLANDS = Internal.createIdentity(Islands.ISLANDS, Islands.ISLANDS.ID);
        public static Identity<LoginHistoriesRecord, Integer> IDENTITY_LOGIN_HISTORIES = Internal.createIdentity(LoginHistories.LOGIN_HISTORIES, LoginHistories.LOGIN_HISTORIES.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AccountsRecord> ACCOUNTS_PKEY = Internal.createUniqueKey(Accounts.ACCOUNTS, "accounts_pkey", Accounts.ACCOUNTS.ID);
        public static final UniqueKey<GuildIslandsRecord> GUILD_ISLANDS_PKEY = Internal.createUniqueKey(GuildIslands.GUILD_ISLANDS, "guild_islands_pkey", GuildIslands.GUILD_ISLANDS.ISLAND_ID);
        public static final UniqueKey<GuildsRecord> GUILDS_PKEY = Internal.createUniqueKey(Guilds.GUILDS, "guilds_pkey", Guilds.GUILDS.ID);
        public static final UniqueKey<IslandersRecord> ISLANDERS_PKEY = Internal.createUniqueKey(Islanders.ISLANDERS, "islanders_pkey", Islanders.ISLANDERS.ID);
        public static final UniqueKey<IslandsRecord> ISLANDS_PKEY = Internal.createUniqueKey(Islands.ISLANDS, "islands_pkey", Islands.ISLANDS.ID);
        public static final UniqueKey<IslandsMembershipRecord> ISLANDS_MEMBERSHIP_PKEY = Internal.createUniqueKey(IslandsMembership.ISLANDS_MEMBERSHIP, "islands_membership_pkey", IslandsMembership.ISLANDS_MEMBERSHIP.ISLANDER_ID);
        public static final UniqueKey<LoginHistoriesRecord> LOGIN_HISTORIES_PKEY = Internal.createUniqueKey(LoginHistories.LOGIN_HISTORIES, "login_histories_pkey", LoginHistories.LOGIN_HISTORIES.ID);
        public static final UniqueKey<SkyblockIslandsRecord> SKYBLOCK_ISLANDS_PKEY = Internal.createUniqueKey(SkyblockIslands.SKYBLOCK_ISLANDS, "skyblock_islands_pkey", SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID);
        public static final UniqueKey<SkyblockIslandsRecord> SKYBLOCK_ISLANDS_X_Z_KEY = Internal.createUniqueKey(SkyblockIslands.SKYBLOCK_ISLANDS, "skyblock_islands_x_z_key", SkyblockIslands.SKYBLOCK_ISLANDS.X, SkyblockIslands.SKYBLOCK_ISLANDS.Z);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<GuildIslandsRecord, IslandsRecord> GUILD_ISLANDS__GUILD_ISLANDS_ISLAND_ID_FKEY = Internal.createForeignKey(pl.subtelny.generated.tables.Keys.ISLANDS_PKEY, GuildIslands.GUILD_ISLANDS, "guild_islands__guild_islands_island_id_fkey", GuildIslands.GUILD_ISLANDS.ISLAND_ID);
        public static final ForeignKey<IslandersRecord, AccountsRecord> ISLANDERS__ISLANDERS_ID_FKEY = Internal.createForeignKey(pl.subtelny.generated.tables.Keys.ACCOUNTS_PKEY, Islanders.ISLANDERS, "islanders__islanders_id_fkey", Islanders.ISLANDERS.ID);
        public static final ForeignKey<IslandsMembershipRecord, IslandersRecord> ISLANDS_MEMBERSHIP__ISLANDS_MEMBERSHIP_ISLANDER_ID_FKEY = Internal.createForeignKey(pl.subtelny.generated.tables.Keys.ISLANDERS_PKEY, IslandsMembership.ISLANDS_MEMBERSHIP, "islands_membership__islands_membership_islander_id_fkey", IslandsMembership.ISLANDS_MEMBERSHIP.ISLANDER_ID);
        public static final ForeignKey<IslandsMembershipRecord, IslandsRecord> ISLANDS_MEMBERSHIP__ISLANDS_MEMBERSHIP_ISLAND_ID_FKEY = Internal.createForeignKey(pl.subtelny.generated.tables.Keys.ISLANDS_PKEY, IslandsMembership.ISLANDS_MEMBERSHIP, "islands_membership__islands_membership_island_id_fkey", IslandsMembership.ISLANDS_MEMBERSHIP.ISLAND_ID);
        public static final ForeignKey<LoginHistoriesRecord, AccountsRecord> LOGIN_HISTORIES__LOGIN_HISTORIES_ACCOUNT_FKEY = Internal.createForeignKey(pl.subtelny.generated.tables.Keys.ACCOUNTS_PKEY, LoginHistories.LOGIN_HISTORIES, "login_histories__login_histories_account_fkey", LoginHistories.LOGIN_HISTORIES.ACCOUNT);
        public static final ForeignKey<SkyblockIslandsRecord, IslandsRecord> SKYBLOCK_ISLANDS__SKYBLOCK_ISLANDS_ISLAND_ID_FKEY = Internal.createForeignKey(pl.subtelny.generated.tables.Keys.ISLANDS_PKEY, SkyblockIslands.SKYBLOCK_ISLANDS, "skyblock_islands__skyblock_islands_island_id_fkey", SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID);
    }
}