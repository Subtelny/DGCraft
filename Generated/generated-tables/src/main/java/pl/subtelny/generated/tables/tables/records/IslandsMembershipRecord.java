/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables.tables.records;


import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import pl.subtelny.generated.tables.enums.Membershiptype;
import pl.subtelny.generated.tables.tables.IslandsMembership;


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
public class IslandsMembershipRecord extends UpdatableRecordImpl<IslandsMembershipRecord> implements Record3<UUID, Integer, Membershiptype> {

    private static final long serialVersionUID = 1177600750;

    /**
     * Setter for <code>public.islands_membership.islander_id</code>.
     */
    public void setIslanderId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.islands_membership.islander_id</code>.
     */
    public UUID getIslanderId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.islands_membership.island_id</code>.
     */
    public void setIslandId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.islands_membership.island_id</code>.
     */
    public Integer getIslandId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.islands_membership.membership_type</code>.
     */
    public void setMembershipType(Membershiptype value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.islands_membership.membership_type</code>.
     */
    public Membershiptype getMembershipType() {
        return (Membershiptype) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, Integer, Membershiptype> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, Integer, Membershiptype> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return IslandsMembership.ISLANDS_MEMBERSHIP.ISLANDER_ID;
    }

    @Override
    public Field<Integer> field2() {
        return IslandsMembership.ISLANDS_MEMBERSHIP.ISLAND_ID;
    }

    @Override
    public Field<Membershiptype> field3() {
        return IslandsMembership.ISLANDS_MEMBERSHIP.MEMBERSHIP_TYPE;
    }

    @Override
    public UUID component1() {
        return getIslanderId();
    }

    @Override
    public Integer component2() {
        return getIslandId();
    }

    @Override
    public Membershiptype component3() {
        return getMembershipType();
    }

    @Override
    public UUID value1() {
        return getIslanderId();
    }

    @Override
    public Integer value2() {
        return getIslandId();
    }

    @Override
    public Membershiptype value3() {
        return getMembershipType();
    }

    @Override
    public IslandsMembershipRecord value1(UUID value) {
        setIslanderId(value);
        return this;
    }

    @Override
    public IslandsMembershipRecord value2(Integer value) {
        setIslandId(value);
        return this;
    }

    @Override
    public IslandsMembershipRecord value3(Membershiptype value) {
        setMembershipType(value);
        return this;
    }

    @Override
    public IslandsMembershipRecord values(UUID value1, Integer value2, Membershiptype value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IslandsMembershipRecord
     */
    public IslandsMembershipRecord() {
        super(IslandsMembership.ISLANDS_MEMBERSHIP);
    }

    /**
     * Create a detached, initialised IslandsMembershipRecord
     */
    public IslandsMembershipRecord(UUID islanderId, Integer islandId, Membershiptype membershipType) {
        super(IslandsMembership.ISLANDS_MEMBERSHIP);

        set(0, islanderId);
        set(1, islandId);
        set(2, membershipType);
    }
}