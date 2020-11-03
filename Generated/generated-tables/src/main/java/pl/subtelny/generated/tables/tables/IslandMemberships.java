/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables.tables;


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
import pl.subtelny.generated.tables.tables.records.IslandMembershipsRecord;


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
public class IslandMemberships extends TableImpl<IslandMembershipsRecord> {

    private static final long serialVersionUID = 537163305;

    /**
     * The reference instance of <code>public.island_memberships</code>
     */
    public static final IslandMemberships ISLAND_MEMBERSHIPS = new IslandMemberships();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IslandMembershipsRecord> getRecordType() {
        return IslandMembershipsRecord.class;
    }

    /**
     * The column <code>public.island_memberships.island_id</code>.
     */
    public final TableField<IslandMembershipsRecord, Integer> ISLAND_ID = createField(DSL.name("island_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.island_memberships.island_member_id</code>.
     */
    public final TableField<IslandMembershipsRecord, String> ISLAND_MEMBER_ID = createField(DSL.name("island_member_id"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>public.island_memberships.owner</code>.
     */
    public final TableField<IslandMembershipsRecord, Boolean> OWNER = createField(DSL.name("owner"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * Create a <code>public.island_memberships</code> table reference
     */
    public IslandMemberships() {
        this(DSL.name("island_memberships"), null);
    }

    /**
     * Create an aliased <code>public.island_memberships</code> table reference
     */
    public IslandMemberships(String alias) {
        this(DSL.name(alias), ISLAND_MEMBERSHIPS);
    }

    /**
     * Create an aliased <code>public.island_memberships</code> table reference
     */
    public IslandMemberships(Name alias) {
        this(alias, ISLAND_MEMBERSHIPS);
    }

    private IslandMemberships(Name alias, Table<IslandMembershipsRecord> aliased) {
        this(alias, aliased, null);
    }

    private IslandMemberships(Name alias, Table<IslandMembershipsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> IslandMemberships(Table<O> child, ForeignKey<O, IslandMembershipsRecord> key) {
        super(child, key, ISLAND_MEMBERSHIPS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ISLAND_MEMBERSHIP_ID);
    }

    @Override
    public UniqueKey<IslandMembershipsRecord> getPrimaryKey() {
        return Keys.ISLAND_MEMBERSHIP_ID;
    }

    @Override
    public List<UniqueKey<IslandMembershipsRecord>> getKeys() {
        return Arrays.<UniqueKey<IslandMembershipsRecord>>asList(Keys.ISLAND_MEMBERSHIP_ID);
    }

    @Override
    public List<ForeignKey<IslandMembershipsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<IslandMembershipsRecord, ?>>asList(Keys.ISLAND_MEMBERSHIPS__ISLAND_ID_FK);
    }

    public Islands islands() {
        return new Islands(this, Keys.ISLAND_MEMBERSHIPS__ISLAND_ID_FK);
    }

    @Override
    public IslandMemberships as(String alias) {
        return new IslandMemberships(DSL.name(alias), this);
    }

    @Override
    public IslandMemberships as(Name alias) {
        return new IslandMemberships(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IslandMemberships rename(String name) {
        return new IslandMemberships(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IslandMemberships rename(Name name) {
        return new IslandMemberships(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
