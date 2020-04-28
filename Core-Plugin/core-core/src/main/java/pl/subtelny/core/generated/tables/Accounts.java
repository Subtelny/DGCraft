/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.core.generated.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import pl.subtelny.core.generated.Indexes;
import pl.subtelny.core.generated.Keys;
import pl.subtelny.core.generated.Public;
import pl.subtelny.core.generated.enums.Citytype;
import pl.subtelny.core.generated.tables.records.AccountsRecord;


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
public class Accounts extends TableImpl<AccountsRecord> {

    private static final long serialVersionUID = 2078730793;

    /**
     * The reference instance of <code>public.accounts</code>
     */
    public static final Accounts ACCOUNTS = new Accounts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccountsRecord> getRecordType() {
        return AccountsRecord.class;
    }

    /**
     * The column <code>public.accounts.id</code>.
     */
    public final TableField<AccountsRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.accounts.name</code>.
     */
    public final TableField<AccountsRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(16).nullable(false), this, "");

    /**
     * The column <code>public.accounts.display_name</code>.
     */
    public final TableField<AccountsRecord, String> DISPLAY_NAME = createField(DSL.name("display_name"), org.jooq.impl.SQLDataType.VARCHAR(16).nullable(false), this, "");

    /**
     * The column <code>public.accounts.city</code>.
     */
    public final TableField<AccountsRecord, Citytype> CITY = createField(DSL.name("city"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(pl.subtelny.core.generated.enums.Citytype.class), this, "");

    /**
     * The column <code>public.accounts.last_online</code>.
     */
    public final TableField<AccountsRecord, Timestamp> LAST_ONLINE = createField(DSL.name("last_online"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>public.accounts</code> table reference
     */
    public Accounts() {
        this(DSL.name("accounts"), null);
    }

    /**
     * Create an aliased <code>public.accounts</code> table reference
     */
    public Accounts(String alias) {
        this(DSL.name(alias), ACCOUNTS);
    }

    /**
     * Create an aliased <code>public.accounts</code> table reference
     */
    public Accounts(Name alias) {
        this(alias, ACCOUNTS);
    }

    private Accounts(Name alias, Table<AccountsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Accounts(Name alias, Table<AccountsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Accounts(Table<O> child, ForeignKey<O, AccountsRecord> key) {
        super(child, key, ACCOUNTS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ACCOUNTS_PKEY);
    }

    @Override
    public UniqueKey<AccountsRecord> getPrimaryKey() {
        return Keys.ACCOUNTS_PKEY;
    }

    @Override
    public List<UniqueKey<AccountsRecord>> getKeys() {
        return Arrays.<UniqueKey<AccountsRecord>>asList(Keys.ACCOUNTS_PKEY);
    }

    @Override
    public Accounts as(String alias) {
        return new Accounts(DSL.name(alias), this);
    }

    @Override
    public Accounts as(Name alias) {
        return new Accounts(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Accounts rename(String name) {
        return new Accounts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Accounts rename(Name name) {
        return new Accounts(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, String, String, Citytype, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}