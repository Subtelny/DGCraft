/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.core.generated.tables;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import pl.subtelny.core.generated.Keys;
import pl.subtelny.core.generated.Public;
import pl.subtelny.core.generated.tables.records.IslandersRecord;


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
public class Islanders extends TableImpl<IslandersRecord> {

    private static final long serialVersionUID = 1868145644;

    /**
     * The reference instance of <code>public.islanders</code>
     */
    public static final Islanders ISLANDERS = new Islanders();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IslandersRecord> getRecordType() {
        return IslandersRecord.class;
    }

    /**
     * The column <code>public.islanders.id</code>.
     */
    public final TableField<IslandersRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.islanders.skyblock_island</code>.
     */
    public final TableField<IslandersRecord, Integer> SKYBLOCK_ISLAND = createField(DSL.name("skyblock_island"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.islanders.guild</code>.
     */
    public final TableField<IslandersRecord, Integer> GUILD = createField(DSL.name("guild"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.islanders</code> table reference
     */
    public Islanders() {
        this(DSL.name("islanders"), null);
    }

    /**
     * Create an aliased <code>public.islanders</code> table reference
     */
    public Islanders(String alias) {
        this(DSL.name(alias), ISLANDERS);
    }

    /**
     * Create an aliased <code>public.islanders</code> table reference
     */
    public Islanders(Name alias) {
        this(alias, ISLANDERS);
    }

    private Islanders(Name alias, Table<IslandersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Islanders(Name alias, Table<IslandersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Islanders(Table<O> child, ForeignKey<O, IslandersRecord> key) {
        super(child, key, ISLANDERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<ForeignKey<IslandersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<IslandersRecord, ?>>asList(Keys.ISLANDERS__ISLANDERS_ID_FKEY, Keys.ISLANDERS__ISLANDERS_SKYBLOCK_ISLAND_FKEY, Keys.ISLANDERS__ISLANDERS_GUILD_FKEY);
    }

    public Accounts accounts() {
        return new Accounts(this, Keys.ISLANDERS__ISLANDERS_ID_FKEY);
    }

    public Islands islands() {
        return new Islands(this, Keys.ISLANDERS__ISLANDERS_SKYBLOCK_ISLAND_FKEY);
    }

    public Guilds guilds() {
        return new Guilds(this, Keys.ISLANDERS__ISLANDERS_GUILD_FKEY);
    }

    @Override
    public Islanders as(String alias) {
        return new Islanders(DSL.name(alias), this);
    }

    @Override
    public Islanders as(Name alias) {
        return new Islanders(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Islanders rename(String name) {
        return new Islanders(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Islanders rename(Name name) {
        return new Islanders(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
