/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import pl.subtelny.generated.tables.Indexes;
import pl.subtelny.generated.tables.Keys;
import pl.subtelny.generated.tables.Public;
import pl.subtelny.generated.tables.tables.records.GuildIslandsRecord;


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
public class GuildIslands extends TableImpl<GuildIslandsRecord> {

    private static final long serialVersionUID = 526367706;

    /**
     * The reference instance of <code>public.guild_islands</code>
     */
    public static final GuildIslands GUILD_ISLANDS = new GuildIslands();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GuildIslandsRecord> getRecordType() {
        return GuildIslandsRecord.class;
    }

    /**
     * The column <code>public.guild_islands.island_id</code>.
     */
    public final TableField<GuildIslandsRecord, Integer> ISLAND_ID = createField(DSL.name("island_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.guild_islands.protection</code>.
     */
    public final TableField<GuildIslandsRecord, Timestamp> PROTECTION = createField(DSL.name("protection"), org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>public.guild_islands.cuboid</code>.
     */
    public final TableField<GuildIslandsRecord, String> CUBOID = createField(DSL.name("cuboid"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * Create a <code>public.guild_islands</code> table reference
     */
    public GuildIslands() {
        this(DSL.name("guild_islands"), null);
    }

    /**
     * Create an aliased <code>public.guild_islands</code> table reference
     */
    public GuildIslands(String alias) {
        this(DSL.name(alias), GUILD_ISLANDS);
    }

    /**
     * Create an aliased <code>public.guild_islands</code> table reference
     */
    public GuildIslands(Name alias) {
        this(alias, GUILD_ISLANDS);
    }

    private GuildIslands(Name alias, Table<GuildIslandsRecord> aliased) {
        this(alias, aliased, null);
    }

    private GuildIslands(Name alias, Table<GuildIslandsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> GuildIslands(Table<O> child, ForeignKey<O, GuildIslandsRecord> key) {
        super(child, key, GUILD_ISLANDS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.GUILD_ISLANDS_PKEY);
    }

    @Override
    public UniqueKey<GuildIslandsRecord> getPrimaryKey() {
        return Keys.GUILD_ISLANDS_PKEY;
    }

    @Override
    public List<UniqueKey<GuildIslandsRecord>> getKeys() {
        return Arrays.<UniqueKey<GuildIslandsRecord>>asList(Keys.GUILD_ISLANDS_PKEY);
    }

    @Override
    public List<ForeignKey<GuildIslandsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GuildIslandsRecord, ?>>asList(Keys.GUILD_ISLANDS__GUILD_ISLANDS_ISLAND_ID_FKEY);
    }

    public Islands islands() {
        return new Islands(this, Keys.GUILD_ISLANDS__GUILD_ISLANDS_ISLAND_ID_FKEY);
    }

    @Override
    public GuildIslands as(String alias) {
        return new GuildIslands(DSL.name(alias), this);
    }

    @Override
    public GuildIslands as(Name alias) {
        return new GuildIslands(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GuildIslands rename(String name) {
        return new GuildIslands(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GuildIslands rename(Name name) {
        return new GuildIslands(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Timestamp, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
