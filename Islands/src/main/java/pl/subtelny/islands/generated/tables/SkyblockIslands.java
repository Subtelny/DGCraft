/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.islands.generated.tables;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import pl.subtelny.islands.generated.Indexes;
import pl.subtelny.islands.generated.Keys;
import pl.subtelny.islands.generated.Public;
import pl.subtelny.islands.generated.tables.records.SkyblockIslandsRecord;


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
public class SkyblockIslands extends TableImpl<SkyblockIslandsRecord> {

    private static final long serialVersionUID = 282910475;

    /**
     * The reference instance of <code>public.skyblock_islands</code>
     */
    public static final SkyblockIslands SKYBLOCK_ISLANDS = new SkyblockIslands();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SkyblockIslandsRecord> getRecordType() {
        return SkyblockIslandsRecord.class;
    }

    /**
     * The column <code>public.skyblock_islands.island_id</code>.
     */
    public final TableField<SkyblockIslandsRecord, Integer> ISLAND_ID = createField(DSL.name("island_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.skyblock_islands.x</code>.
     */
    public final TableField<SkyblockIslandsRecord, Integer> X = createField(DSL.name("x"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.skyblock_islands.z</code>.
     */
    public final TableField<SkyblockIslandsRecord, Integer> Z = createField(DSL.name("z"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.skyblock_islands.owner</code>.
     */
    public final TableField<SkyblockIslandsRecord, UUID> OWNER = createField(DSL.name("owner"), org.jooq.impl.SQLDataType.UUID, this, "");

    /**
     * The column <code>public.skyblock_islands.points</code>.
     */
    public final TableField<SkyblockIslandsRecord, Integer> POINTS = createField(DSL.name("points"), org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.skyblock_islands.extend_level</code>.
     */
    public final TableField<SkyblockIslandsRecord, Integer> EXTEND_LEVEL = createField(DSL.name("extend_level"), org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * Create a <code>public.skyblock_islands</code> table reference
     */
    public SkyblockIslands() {
        this(DSL.name("skyblock_islands"), null);
    }

    /**
     * Create an aliased <code>public.skyblock_islands</code> table reference
     */
    public SkyblockIslands(String alias) {
        this(DSL.name(alias), SKYBLOCK_ISLANDS);
    }

    /**
     * Create an aliased <code>public.skyblock_islands</code> table reference
     */
    public SkyblockIslands(Name alias) {
        this(alias, SKYBLOCK_ISLANDS);
    }

    private SkyblockIslands(Name alias, Table<SkyblockIslandsRecord> aliased) {
        this(alias, aliased, null);
    }

    private SkyblockIslands(Name alias, Table<SkyblockIslandsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> SkyblockIslands(Table<O> child, ForeignKey<O, SkyblockIslandsRecord> key) {
        super(child, key, SKYBLOCK_ISLANDS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SKYBLOCK_ISLANDS_OWNER_KEY, Indexes.SKYBLOCK_ISLANDS_PKEY, Indexes.SKYBLOCK_ISLANDS_X_Z_KEY);
    }

    @Override
    public UniqueKey<SkyblockIslandsRecord> getPrimaryKey() {
        return Keys.SKYBLOCK_ISLANDS_PKEY;
    }

    @Override
    public List<UniqueKey<SkyblockIslandsRecord>> getKeys() {
        return Arrays.<UniqueKey<SkyblockIslandsRecord>>asList(Keys.SKYBLOCK_ISLANDS_PKEY, Keys.SKYBLOCK_ISLANDS_X_Z_KEY, Keys.SKYBLOCK_ISLANDS_OWNER_KEY);
    }

    @Override
    public List<ForeignKey<SkyblockIslandsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<SkyblockIslandsRecord, ?>>asList(Keys.SKYBLOCK_ISLANDS__SKYBLOCK_ISLANDS_ISLAND_ID_FKEY);
    }

    public Islands islands() {
        return new Islands(this, Keys.SKYBLOCK_ISLANDS__SKYBLOCK_ISLANDS_ISLAND_ID_FKEY);
    }

    @Override
    public SkyblockIslands as(String alias) {
        return new SkyblockIslands(DSL.name(alias), this);
    }

    @Override
    public SkyblockIslands as(Name alias) {
        return new SkyblockIslands(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SkyblockIslands rename(String name) {
        return new SkyblockIslands(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SkyblockIslands rename(Name name) {
        return new SkyblockIslands(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, Integer, Integer, UUID, Integer, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
