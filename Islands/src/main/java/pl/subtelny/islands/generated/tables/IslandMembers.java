/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.islands.generated.tables;


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

import pl.subtelny.islands.generated.Indexes;
import pl.subtelny.islands.generated.Keys;
import pl.subtelny.islands.generated.Public;
import pl.subtelny.islands.generated.enums.Islandmembertype;
import pl.subtelny.islands.generated.tables.records.IslandMembersRecord;


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
public class IslandMembers extends TableImpl<IslandMembersRecord> {

    private static final long serialVersionUID = -355325417;

    /**
     * The reference instance of <code>public.island_members</code>
     */
    public static final IslandMembers ISLAND_MEMBERS = new IslandMembers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IslandMembersRecord> getRecordType() {
        return IslandMembersRecord.class;
    }

    /**
     * The column <code>public.island_members.island_id</code>.
     */
    public final TableField<IslandMembersRecord, Integer> ISLAND_ID = createField(DSL.name("island_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.island_members.id</code>.
     */
    public final TableField<IslandMembersRecord, String> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.island_members.member_type</code>.
     */
    public final TableField<IslandMembersRecord, Islandmembertype> MEMBER_TYPE = createField(DSL.name("member_type"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(pl.subtelny.islands.generated.enums.Islandmembertype.class), this, "");

    /**
     * Create a <code>public.island_members</code> table reference
     */
    public IslandMembers() {
        this(DSL.name("island_members"), null);
    }

    /**
     * Create an aliased <code>public.island_members</code> table reference
     */
    public IslandMembers(String alias) {
        this(DSL.name(alias), ISLAND_MEMBERS);
    }

    /**
     * Create an aliased <code>public.island_members</code> table reference
     */
    public IslandMembers(Name alias) {
        this(alias, ISLAND_MEMBERS);
    }

    private IslandMembers(Name alias, Table<IslandMembersRecord> aliased) {
        this(alias, aliased, null);
    }

    private IslandMembers(Name alias, Table<IslandMembersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> IslandMembers(Table<O> child, ForeignKey<O, IslandMembersRecord> key) {
        super(child, key, ISLAND_MEMBERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ISLAND_MEMBERS_PKEY);
    }

    @Override
    public UniqueKey<IslandMembersRecord> getPrimaryKey() {
        return Keys.ISLAND_MEMBERS_PKEY;
    }

    @Override
    public List<UniqueKey<IslandMembersRecord>> getKeys() {
        return Arrays.<UniqueKey<IslandMembersRecord>>asList(Keys.ISLAND_MEMBERS_PKEY);
    }

    @Override
    public List<ForeignKey<IslandMembersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<IslandMembersRecord, ?>>asList(Keys.ISLAND_MEMBERS__ISLAND_MEMBERS_ISLAND_ID_FKEY);
    }

    public Islands islands() {
        return new Islands(this, Keys.ISLAND_MEMBERS__ISLAND_MEMBERS_ISLAND_ID_FKEY);
    }

    @Override
    public IslandMembers as(String alias) {
        return new IslandMembers(DSL.name(alias), this);
    }

    @Override
    public IslandMembers as(Name alias) {
        return new IslandMembers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IslandMembers rename(String name) {
        return new IslandMembers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IslandMembers rename(Name name) {
        return new IslandMembers(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Islandmembertype> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}