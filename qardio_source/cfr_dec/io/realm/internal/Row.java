/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.LinkView;
import io.realm.internal.Table;
import java.util.Date;

public interface Row {
    public byte[] getBinaryByteArray(long var1);

    public boolean getBoolean(long var1);

    public long getColumnCount();

    public long getColumnIndex(String var1);

    public String getColumnName(long var1);

    public RealmFieldType getColumnType(long var1);

    public Date getDate(long var1);

    public double getDouble(long var1);

    public float getFloat(long var1);

    public long getIndex();

    public long getLink(long var1);

    public LinkView getLinkList(long var1);

    public long getLong(long var1);

    public String getString(long var1);

    public Table getTable();

    public boolean isAttached();

    public boolean isNull(long var1);

    public boolean isNullLink(long var1);

    public void nullifyLink(long var1);

    public void setBoolean(long var1, boolean var3);

    public void setFloat(long var1, float var3);

    public void setLink(long var1, long var3);

    public void setLong(long var1, long var3);

    public void setNull(long var1);

    public void setString(long var1, String var3);
}

