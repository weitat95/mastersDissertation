/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.NativeContext;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;

public class CheckedRow
extends UncheckedRow {
    private UncheckedRow originalRow;

    private CheckedRow(NativeContext nativeContext, Table table, long l) {
        super(nativeContext, table, l);
    }

    private CheckedRow(UncheckedRow uncheckedRow) {
        super(uncheckedRow);
        this.originalRow = uncheckedRow;
    }

    public static CheckedRow get(NativeContext nativeContext, Table table, long l) {
        return new CheckedRow(nativeContext, table, table.nativeGetRowPtr(table.getNativePtr(), l));
    }

    public static CheckedRow getFromRow(UncheckedRow uncheckedRow) {
        return new CheckedRow(uncheckedRow);
    }

    @Override
    public boolean isNull(long l) {
        return super.isNull(l);
    }

    @Override
    public boolean isNullLink(long l) {
        RealmFieldType realmFieldType = this.getColumnType(l);
        if (realmFieldType == RealmFieldType.OBJECT || realmFieldType == RealmFieldType.LIST) {
            return super.isNullLink(l);
        }
        return false;
    }

    @Override
    protected native boolean nativeGetBoolean(long var1, long var3);

    @Override
    protected native byte[] nativeGetByteArray(long var1, long var3);

    @Override
    protected native long nativeGetColumnCount(long var1);

    @Override
    protected native long nativeGetColumnIndex(long var1, String var3);

    @Override
    protected native String nativeGetColumnName(long var1, long var3);

    @Override
    protected native int nativeGetColumnType(long var1, long var3);

    @Override
    protected native double nativeGetDouble(long var1, long var3);

    @Override
    protected native float nativeGetFloat(long var1, long var3);

    @Override
    protected native long nativeGetLink(long var1, long var3);

    @Override
    protected native long nativeGetLinkView(long var1, long var3);

    @Override
    protected native long nativeGetLong(long var1, long var3);

    @Override
    protected native String nativeGetString(long var1, long var3);

    @Override
    protected native long nativeGetTimestamp(long var1, long var3);

    @Override
    protected native boolean nativeIsNullLink(long var1, long var3);

    @Override
    protected native void nativeNullifyLink(long var1, long var3);

    @Override
    protected native void nativeSetBoolean(long var1, long var3, boolean var5);

    @Override
    protected native void nativeSetByteArray(long var1, long var3, byte[] var5);

    @Override
    protected native void nativeSetDouble(long var1, long var3, double var5);

    @Override
    protected native void nativeSetFloat(long var1, long var3, float var5);

    @Override
    protected native void nativeSetLink(long var1, long var3, long var5);

    @Override
    protected native void nativeSetLong(long var1, long var3, long var5);

    @Override
    protected native void nativeSetString(long var1, long var3, String var5);

    @Override
    protected native void nativeSetTimestamp(long var1, long var3, long var5);

    @Override
    public void setNull(long l) {
        if (this.getColumnType(l) == RealmFieldType.BINARY) {
            super.setBinaryByteArray(l, null);
            return;
        }
        super.setNull(l);
    }
}

