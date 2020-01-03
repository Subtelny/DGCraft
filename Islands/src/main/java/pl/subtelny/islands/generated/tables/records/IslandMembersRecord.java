/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.islands.generated.tables.records;


import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import pl.subtelny.islands.generated.enums.Islandmembertype;
import pl.subtelny.islands.generated.tables.IslandMembers;


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
public class IslandMembersRecord extends UpdatableRecordImpl<IslandMembersRecord> implements Record3<Integer, String, Islandmembertype> {

    private static final long serialVersionUID = 1819459402;

    /**
     * Setter for <code>public.island_members.island_id</code>.
     */
    public void setIslandId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.island_members.island_id</code>.
     */
    public Integer getIslandId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.island_members.id</code>.
     */
    public void setId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.island_members.id</code>.
     */
    public String getId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.island_members.member_type</code>.
     */
    public void setMemberType(Islandmembertype value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.island_members.member_type</code>.
     */
    public Islandmembertype getMemberType() {
        return (Islandmembertype) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Integer, String, Islandmembertype> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Islandmembertype> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, Islandmembertype> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return IslandMembers.ISLAND_MEMBERS.ISLAND_ID;
    }

    @Override
    public Field<String> field2() {
        return IslandMembers.ISLAND_MEMBERS.ID;
    }

    @Override
    public Field<Islandmembertype> field3() {
        return IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE;
    }

    @Override
    public Integer component1() {
        return getIslandId();
    }

    @Override
    public String component2() {
        return getId();
    }

    @Override
    public Islandmembertype component3() {
        return getMemberType();
    }

    @Override
    public Integer value1() {
        return getIslandId();
    }

    @Override
    public String value2() {
        return getId();
    }

    @Override
    public Islandmembertype value3() {
        return getMemberType();
    }

    @Override
    public IslandMembersRecord value1(Integer value) {
        setIslandId(value);
        return this;
    }

    @Override
    public IslandMembersRecord value2(String value) {
        setId(value);
        return this;
    }

    @Override
    public IslandMembersRecord value3(Islandmembertype value) {
        setMemberType(value);
        return this;
    }

    @Override
    public IslandMembersRecord values(Integer value1, String value2, Islandmembertype value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IslandMembersRecord
     */
    public IslandMembersRecord() {
        super(IslandMembers.ISLAND_MEMBERS);
    }

    /**
     * Create a detached, initialised IslandMembersRecord
     */
    public IslandMembersRecord(Integer islandId, String id, Islandmembertype memberType) {
        super(IslandMembers.ISLAND_MEMBERS);

        set(0, islandId);
        set(1, id);
        set(2, memberType);
    }
}