/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.core.generated.tables.records;


import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import pl.subtelny.core.generated.tables.Islanders;


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
public class IslandersRecord extends UpdatableRecordImpl<IslandersRecord> implements Record3<UUID, Integer, Integer> {

    private static final long serialVersionUID = -886651642;

    /**
     * Setter for <code>public.islanders.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.islanders.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.islanders.skyblock_island</code>.
     */
    public void setSkyblockIsland(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.islanders.skyblock_island</code>.
     */
    public Integer getSkyblockIsland() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.islanders.guild</code>.
     */
    public void setGuild(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.islanders.guild</code>.
     */
    public Integer getGuild() {
        return (Integer) get(2);
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
    public Row3<UUID, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, Integer, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Islanders.ISLANDERS.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Islanders.ISLANDERS.SKYBLOCK_ISLAND;
    }

    @Override
    public Field<Integer> field3() {
        return Islanders.ISLANDERS.GUILD;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getSkyblockIsland();
    }

    @Override
    public Integer component3() {
        return getGuild();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getSkyblockIsland();
    }

    @Override
    public Integer value3() {
        return getGuild();
    }

    @Override
    public IslandersRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public IslandersRecord value2(Integer value) {
        setSkyblockIsland(value);
        return this;
    }

    @Override
    public IslandersRecord value3(Integer value) {
        setGuild(value);
        return this;
    }

    @Override
    public IslandersRecord values(UUID value1, Integer value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IslandersRecord
     */
    public IslandersRecord() {
        super(Islanders.ISLANDERS);
    }

    /**
     * Create a detached, initialised IslandersRecord
     */
    public IslandersRecord(UUID id, Integer skyblockIsland, Integer guild) {
        super(Islanders.ISLANDERS);

        set(0, id);
        set(1, skyblockIsland);
        set(2, guild);
    }
}
