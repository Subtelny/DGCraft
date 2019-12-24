/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.core.generated.tables.records;


import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import pl.subtelny.core.generated.tables.AnemiaLoginHistory;


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
public class AnemiaLoginHistoryRecord extends UpdatableRecordImpl<AnemiaLoginHistoryRecord> implements Record4<Integer, Timestamp, Timestamp, UUID> {

    private static final long serialVersionUID = 1397945556;

    /**
     * Setter for <code>public.anemia_login_history.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.anemia_login_history.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.anemia_login_history.start_date</code>.
     */
    public void setStartDate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.anemia_login_history.start_date</code>.
     */
    public Timestamp getStartDate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>public.anemia_login_history.end_date</code>.
     */
    public void setEndDate(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.anemia_login_history.end_date</code>.
     */
    public Timestamp getEndDate() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>public.anemia_login_history.account</code>.
     */
    public void setAccount(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.anemia_login_history.account</code>.
     */
    public UUID getAccount() {
        return (UUID) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Timestamp, Timestamp, UUID> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Timestamp, Timestamp, UUID> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AnemiaLoginHistory.ANEMIA_LOGIN_HISTORY.ID;
    }

    @Override
    public Field<Timestamp> field2() {
        return AnemiaLoginHistory.ANEMIA_LOGIN_HISTORY.START_DATE;
    }

    @Override
    public Field<Timestamp> field3() {
        return AnemiaLoginHistory.ANEMIA_LOGIN_HISTORY.END_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return AnemiaLoginHistory.ANEMIA_LOGIN_HISTORY.ACCOUNT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Timestamp component2() {
        return getStartDate();
    }

    @Override
    public Timestamp component3() {
        return getEndDate();
    }

    @Override
    public UUID component4() {
        return getAccount();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Timestamp value2() {
        return getStartDate();
    }

    @Override
    public Timestamp value3() {
        return getEndDate();
    }

    @Override
    public UUID value4() {
        return getAccount();
    }

    @Override
    public AnemiaLoginHistoryRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public AnemiaLoginHistoryRecord value2(Timestamp value) {
        setStartDate(value);
        return this;
    }

    @Override
    public AnemiaLoginHistoryRecord value3(Timestamp value) {
        setEndDate(value);
        return this;
    }

    @Override
    public AnemiaLoginHistoryRecord value4(UUID value) {
        setAccount(value);
        return this;
    }

    @Override
    public AnemiaLoginHistoryRecord values(Integer value1, Timestamp value2, Timestamp value3, UUID value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AnemiaLoginHistoryRecord
     */
    public AnemiaLoginHistoryRecord() {
        super(AnemiaLoginHistory.ANEMIA_LOGIN_HISTORY);
    }

    /**
     * Create a detached, initialised AnemiaLoginHistoryRecord
     */
    public AnemiaLoginHistoryRecord(Integer id, Timestamp startDate, Timestamp endDate, UUID account) {
        super(AnemiaLoginHistory.ANEMIA_LOGIN_HISTORY);

        set(0, id);
        set(1, startDate);
        set(2, endDate);
        set(3, account);
    }
}