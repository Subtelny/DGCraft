/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.generated.tables.tables.records;


import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import pl.subtelny.generated.tables.tables.LoginHistories;


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
public class LoginHistoriesRecord extends UpdatableRecordImpl<LoginHistoriesRecord> implements Record4<Integer, Timestamp, Timestamp, UUID> {

    private static final long serialVersionUID = -1983942702;

    /**
     * Setter for <code>public.login_histories.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.login_histories.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.login_histories.login_time</code>.
     */
    public void setLoginTime(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.login_histories.login_time</code>.
     */
    public Timestamp getLoginTime() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>public.login_histories.logout_time</code>.
     */
    public void setLogoutTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.login_histories.logout_time</code>.
     */
    public Timestamp getLogoutTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>public.login_histories.account</code>.
     */
    public void setAccount(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.login_histories.account</code>.
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
        return LoginHistories.LOGIN_HISTORIES.ID;
    }

    @Override
    public Field<Timestamp> field2() {
        return LoginHistories.LOGIN_HISTORIES.LOGIN_TIME;
    }

    @Override
    public Field<Timestamp> field3() {
        return LoginHistories.LOGIN_HISTORIES.LOGOUT_TIME;
    }

    @Override
    public Field<UUID> field4() {
        return LoginHistories.LOGIN_HISTORIES.ACCOUNT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Timestamp component2() {
        return getLoginTime();
    }

    @Override
    public Timestamp component3() {
        return getLogoutTime();
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
        return getLoginTime();
    }

    @Override
    public Timestamp value3() {
        return getLogoutTime();
    }

    @Override
    public UUID value4() {
        return getAccount();
    }

    @Override
    public LoginHistoriesRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public LoginHistoriesRecord value2(Timestamp value) {
        setLoginTime(value);
        return this;
    }

    @Override
    public LoginHistoriesRecord value3(Timestamp value) {
        setLogoutTime(value);
        return this;
    }

    @Override
    public LoginHistoriesRecord value4(UUID value) {
        setAccount(value);
        return this;
    }

    @Override
    public LoginHistoriesRecord values(Integer value1, Timestamp value2, Timestamp value3, UUID value4) {
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
     * Create a detached LoginHistoriesRecord
     */
    public LoginHistoriesRecord() {
        super(LoginHistories.LOGIN_HISTORIES);
    }

    /**
     * Create a detached, initialised LoginHistoriesRecord
     */
    public LoginHistoriesRecord(Integer id, Timestamp loginTime, Timestamp logoutTime, UUID account) {
        super(LoginHistories.LOGIN_HISTORIES);

        set(0, id);
        set(1, loginTime);
        set(2, logoutTime);
        set(3, account);
    }
}