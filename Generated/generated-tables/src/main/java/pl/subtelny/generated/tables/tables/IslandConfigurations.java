/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.JSON;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import pl.subtelny.generated.tables.Indexes;
import pl.subtelny.generated.tables.Keys;
import pl.subtelny.generated.tables.Public;
import pl.subtelny.generated.tables.tables.records.IslandConfigurationsRecord;


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
public class IslandConfigurations extends TableImpl<IslandConfigurationsRecord> {

    private static final long serialVersionUID = 1833148441;

    /**
     * The reference instance of <code>public.island_configurations</code>
     */
    public static final IslandConfigurations ISLAND_CONFIGURATIONS = new IslandConfigurations();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IslandConfigurationsRecord> getRecordType() {
        return IslandConfigurationsRecord.class;
    }

    /**
     * The column <code>public.island_configurations.island_id</code>.
     */
    public final TableField<IslandConfigurationsRecord, Integer> ISLAND_ID = createField(DSL.name("island_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('island_configurations_island_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.island_configurations.configuration</code>.
     */
    public final TableField<IslandConfigurationsRecord, JSON> CONFIGURATION = createField(DSL.name("configuration"), org.jooq.impl.SQLDataType.JSON.nullable(false), this, "");

    /**
     * Create a <code>public.island_configurations</code> table reference
     */
    public IslandConfigurations() {
        this(DSL.name("island_configurations"), null);
    }

    /**
     * Create an aliased <code>public.island_configurations</code> table reference
     */
    public IslandConfigurations(String alias) {
        this(DSL.name(alias), ISLAND_CONFIGURATIONS);
    }

    /**
     * Create an aliased <code>public.island_configurations</code> table reference
     */
    public IslandConfigurations(Name alias) {
        this(alias, ISLAND_CONFIGURATIONS);
    }

    private IslandConfigurations(Name alias, Table<IslandConfigurationsRecord> aliased) {
        this(alias, aliased, null);
    }

    private IslandConfigurations(Name alias, Table<IslandConfigurationsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> IslandConfigurations(Table<O> child, ForeignKey<O, IslandConfigurationsRecord> key) {
        super(child, key, ISLAND_CONFIGURATIONS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ISLAND_ID_PK);
    }

    @Override
    public Identity<IslandConfigurationsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ISLAND_CONFIGURATIONS;
    }

    @Override
    public UniqueKey<IslandConfigurationsRecord> getPrimaryKey() {
        return Keys.ISLAND_ID_PK;
    }

    @Override
    public List<UniqueKey<IslandConfigurationsRecord>> getKeys() {
        return Arrays.<UniqueKey<IslandConfigurationsRecord>>asList(Keys.ISLAND_ID_PK);
    }

    @Override
    public List<ForeignKey<IslandConfigurationsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<IslandConfigurationsRecord, ?>>asList(Keys.ISLAND_CONFIGURATIONS__ISLAND_ID_FOREIGN);
    }

    public Islands islands() {
        return new Islands(this, Keys.ISLAND_CONFIGURATIONS__ISLAND_ID_FOREIGN);
    }

    @Override
    public IslandConfigurations as(String alias) {
        return new IslandConfigurations(DSL.name(alias), this);
    }

    @Override
    public IslandConfigurations as(Name alias) {
        return new IslandConfigurations(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IslandConfigurations rename(String name) {
        return new IslandConfigurations(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IslandConfigurations rename(Name name) {
        return new IslandConfigurations(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, JSON> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
