/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.LinkView;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.Date;

public class UncheckedRow
implements NativeObject,
Row {
    private static final long nativeFinalizerPtr = UncheckedRow.nativeGetFinalizerPtr();
    private final NativeContext context;
    private final long nativePtr;
    private final Table parent;

    UncheckedRow(NativeContext nativeContext, Table table, long l) {
        this.context = nativeContext;
        this.parent = table;
        this.nativePtr = l;
        nativeContext.addReference(this);
    }

    UncheckedRow(UncheckedRow uncheckedRow) {
        this.context = uncheckedRow.context;
        this.parent = uncheckedRow.parent;
        this.nativePtr = uncheckedRow.nativePtr;
    }

    static UncheckedRow getByRowIndex(NativeContext nativeContext, Table table, long l) {
        return new UncheckedRow(nativeContext, table, table.nativeGetRowPtr(table.getNativePtr(), l));
    }

    static UncheckedRow getByRowPointer(NativeContext nativeContext, Table table, long l) {
        return new UncheckedRow(nativeContext, table, l);
    }

    private static native long nativeGetFinalizerPtr();

    @Override
    public byte[] getBinaryByteArray(long l) {
        return this.nativeGetByteArray(this.nativePtr, l);
    }

    @Override
    public boolean getBoolean(long l) {
        return this.nativeGetBoolean(this.nativePtr, l);
    }

    @Override
    public long getColumnCount() {
        return this.nativeGetColumnCount(this.nativePtr);
    }

    @Override
    public long getColumnIndex(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Column name can not be null.");
        }
        return this.nativeGetColumnIndex(this.nativePtr, string2);
    }

    @Override
    public String getColumnName(long l) {
        return this.nativeGetColumnName(this.nativePtr, l);
    }

    @Override
    public RealmFieldType getColumnType(long l) {
        return RealmFieldType.fromNativeValue(this.nativeGetColumnType(this.nativePtr, l));
    }

    @Override
    public Date getDate(long l) {
        return new Date(this.nativeGetTimestamp(this.nativePtr, l));
    }

    @Override
    public double getDouble(long l) {
        return this.nativeGetDouble(this.nativePtr, l);
    }

    @Override
    public float getFloat(long l) {
        return this.nativeGetFloat(this.nativePtr, l);
    }

    @Override
    public long getIndex() {
        return this.nativeGetIndex(this.nativePtr);
    }

    @Override
    public long getLink(long l) {
        return this.nativeGetLink(this.nativePtr, l);
    }

    @Override
    public LinkView getLinkList(long l) {
        long l2 = this.nativeGetLinkView(this.nativePtr, l);
        return new LinkView(this.context, this.parent, l, l2);
    }

    @Override
    public long getLong(long l) {
        return this.nativeGetLong(this.nativePtr, l);
    }

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override
    public String getString(long l) {
        return this.nativeGetString(this.nativePtr, l);
    }

    @Override
    public Table getTable() {
        return this.parent;
    }

    @Override
    public boolean isAttached() {
        return this.nativePtr != 0L && this.nativeIsAttached(this.nativePtr);
    }

    @Override
    public boolean isNull(long l) {
        return this.nativeIsNull(this.nativePtr, l);
    }

    @Override
    public boolean isNullLink(long l) {
        return this.nativeIsNullLink(this.nativePtr, l);
    }

    protected native boolean nativeGetBoolean(long var1, long var3);

    protected native byte[] nativeGetByteArray(long var1, long var3);

    protected native long nativeGetColumnCount(long var1);

    protected native long nativeGetColumnIndex(long var1, String var3);

    protected native String nativeGetColumnName(long var1, long var3);

    protected native int nativeGetColumnType(long var1, long var3);

    protected native double nativeGetDouble(long var1, long var3);

    protected native float nativeGetFloat(long var1, long var3);

    protected native long nativeGetIndex(long var1);

    protected native long nativeGetLink(long var1, long var3);

    protected native long nativeGetLinkView(long var1, long var3);

    protected native long nativeGetLong(long var1, long var3);

    protected native String nativeGetString(long var1, long var3);

    protected native long nativeGetTimestamp(long var1, long var3);

    protected native boolean nativeHasColumn(long var1, String var3);

    protected native boolean nativeIsAttached(long var1);

    protected native boolean nativeIsNull(long var1, long var3);

    protected native boolean nativeIsNullLink(long var1, long var3);

    protected native void nativeNullifyLink(long var1, long var3);

    protected native void nativeSetBoolean(long var1, long var3, boolean var5);

    protected native void nativeSetByteArray(long var1, long var3, byte[] var5);

    protected native void nativeSetDouble(long var1, long var3, double var5);

    protected native void nativeSetFloat(long var1, long var3, float var5);

    protected native void nativeSetLink(long var1, long var3, long var5);

    protected native void nativeSetLong(long var1, long var3, long var5);

    protected native void nativeSetNull(long var1, long var3);

    protected native void nativeSetString(long var1, long var3, String var5);

    protected native void nativeSetTimestamp(long var1, long var3, long var5);

    @Override
    public void nullifyLink(long l) {
        this.parent.checkImmutable();
        this.nativeNullifyLink(this.nativePtr, l);
    }

    public void setBinaryByteArray(long l, byte[] arrby) {
        this.parent.checkImmutable();
        this.nativeSetByteArray(this.nativePtr, l, arrby);
    }

    @Override
    public void setBoolean(long l, boolean bl) {
        this.parent.checkImmutable();
        this.nativeSetBoolean(this.nativePtr, l, bl);
    }

    @Override
    public void setFloat(long l, float f) {
        this.parent.checkImmutable();
        this.nativeSetFloat(this.nativePtr, l, f);
    }

    @Override
    public void setLink(long l, long l2) {
        this.parent.checkImmutable();
        this.nativeSetLink(this.nativePtr, l, l2);
    }

    @Override
    public void setLong(long l, long l2) {
        this.parent.checkImmutable();
        this.getTable().checkIntValueIsLegal(l, this.getIndex(), l2);
        this.nativeSetLong(this.nativePtr, l, l2);
    }

    @Override
    public void setNull(long l) {
        this.parent.checkImmutable();
        this.getTable().checkDuplicatedNullForPrimaryKeyValue(l, this.getIndex());
        this.nativeSetNull(this.nativePtr, l);
    }

    @Override
    public void setString(long l, String string2) {
        this.parent.checkImmutable();
        if (string2 == null) {
            this.getTable().checkDuplicatedNullForPrimaryKeyValue(l, this.getIndex());
            this.nativeSetNull(this.nativePtr, l);
            return;
        }
        this.getTable().checkStringValueIsLegal(l, this.getIndex(), string2);
        this.nativeSetString(this.nativePtr, l, string2);
    }
}

