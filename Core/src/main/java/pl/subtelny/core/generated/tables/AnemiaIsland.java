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
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import pl.subtelny.core.generated.Indexes;
import pl.subtelny.core.generated.Keys;
import pl.subtelny.core.generated.Public;
import pl.subtelny.core.generated.tables.records.AnemiaIslandRecord;


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
public class AnemiaIsland extends TableImpl<AnemiaIslandRecord> {

    private static final long serialVersionUID = 1975284005;

    /**
     * The reference instance of <code>public.anemia_island</code>
     */
    public static final AnemiaIsland ANEMIA_ISLAND = new AnemiaIsland();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AnemiaIslandRecord> getRecordType() {
        return AnemiaIslandRecord.class;
    }

    /**
     * The column <code>public.anemia_island.x</code>.
     */
    public final TableField<AnemiaIslandRecord, Integer> X = createField(DSL.name("x"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.anemia_island.y</code>.
     */
    public final TableField<AnemiaIslandRecord, Integer> Y = createField(DSL.name("y"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.anemia_island.owner</code>.
     */
    public final TableField<AnemiaIslandRecord, UUID> OWNER = createField(DSL.name("owner"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.anemia_island.created_date</code>.
     */
    public final TableField<AnemiaIslandRecord, Timestamp> CREATED_DATE = createField(DSL.name("created_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>public.anemia_island</code> table reference
     */
    public AnemiaIsland() {
        this(DSL.name("anemia_island"), null);
    }

    /**
     * Create an aliased <code>public.anemia_island</code> table reference
     */
    public AnemiaIsland(String alias) {
        this(DSL.name(alias), ANEMIA_ISLAND);
    }

    /**
     * Create an aliased <code>public.anemia_island</code> table reference
     */
    public AnemiaIsland(Name alias) {
        this(alias, ANEMIA_ISLAND);
    }

    private AnemiaIsland(Name alias, Table<AnemiaIslandRecord> aliased) {
        this(alias, aliased, null);
    }

    private AnemiaIsland(Name alias, Table<AnemiaIslandRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> AnemiaIsland(Table<O> child, ForeignKey<O, AnemiaIslandRecord> key) {
        super(child, key, ANEMIA_ISLAND);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ANEMIA_ISLAND_OWNER_KEY, Indexes.ANEMIA_ISLAND_PKEY);
    }

    @Override
    public UniqueKey<AnemiaIslandRecord> getPrimaryKey() {
        return Keys.ANEMIA_ISLAND_PKEY;
    }

    @Override
    public List<UniqueKey<AnemiaIslandRecord>> getKeys() {
        return Arrays.<UniqueKey<AnemiaIslandRecord>>asList(Keys.ANEMIA_ISLAND_PKEY, Keys.ANEMIA_ISLAND_OWNER_KEY);
    }

    @Override
    public AnemiaIsland as(String alias) {
        return new AnemiaIsland(DSL.name(alias), this);
    }

    @Override
    public AnemiaIsland as(Name alias) {
        return new AnemiaIsland(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AnemiaIsland rename(String name) {
        return new AnemiaIsland(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AnemiaIsland rename(Name name) {
        return new AnemiaIsland(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, UUID, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}