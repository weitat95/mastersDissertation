/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.LinkView;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.Date;

public enum InvalidRow implements Row
{
    INSTANCE;


    private RuntimeException getStubException() {
        return new IllegalStateException("Object is no longer managed by Realm. Has it been deleted?");
    }

    @Override
    public byte[] getBinaryByteArray(long l) {
        throw this.getStubException();
    }

    @Override
    public boolean getBoolean(long l) {
        throw this.getStubException();
    }

    @Override
    public long getColumnCount() {
        throw this.getStubException();
    }

    @Override
    public long getColumnIndex(String string2) {
        throw this.getStubException();
    }

    @Override
    public String getColumnName(long l) {
        throw this.getStubException();
    }

    @Override
    public RealmFieldType getColumnType(long l) {
        throw this.getStubException();
    }

    @Override
    public Date getDate(long l) {
        throw this.getStubException();
    }

    @Override
    public double getDouble(long l) {
        throw this.getStubException();
    }

    @Override
    public float getFloat(long l) {
        throw this.getStubException();
    }

    @Override
    public long getIndex() {
        throw this.getStubException();
    }

    @Override
    public long getLink(long l) {
        throw this.getStubException();
    }

    @Override
    public LinkView getLinkList(long l) {
        throw this.getStubException();
    }

    @Override
    public long getLong(long l) {
        throw this.getStubException();
    }

    @Override
    public String getString(long l) {
        throw this.getStubException();
    }

    @Override
    public Table getTable() {
        throw this.getStubException();
    }

    @Override
    public boolean isAttached() {
        return false;
    }

    @Override
    public boolean isNull(long l) {
        throw this.getStubException();
    }

    @Override
    public boolean isNullLink(long l) {
        throw this.getStubException();
    }

    @Override
    public void nullifyLink(long l) {
        throw this.getStubException();
    }

    @Override
    public void setBoolean(long l, boolean bl) {
        throw this.getStubException();
    }

    @Override
    public void setFloat(long l, float f) {
        throw this.getStubException();
    }

    @Override
    public void setLink(long l, long l2) {
        throw this.getStubException();
    }

    @Override
    public void setLong(long l, long l2) {
        throw this.getStubException();
    }

    @Override
    public void setNull(long l) {
        throw this.getStubException();
    }

    @Override
    public void setString(long l, String string2) {
        throw this.getStubException();
    }
}

