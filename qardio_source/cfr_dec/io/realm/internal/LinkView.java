/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.Table;

public class LinkView
implements NativeObject {
    private static final long nativeFinalizerPtr = LinkView.nativeGetFinalizerPtr();
    final long columnIndexInParent;
    private final NativeContext context;
    private final long nativePtr;
    final Table parent;

    public LinkView(NativeContext nativeContext, Table table, long l, long l2) {
        this.context = nativeContext;
        this.parent = table;
        this.columnIndexInParent = l;
        this.nativePtr = l2;
        nativeContext.addReference(this);
    }

    private void checkImmutable() {
        if (this.parent.isImmutable()) {
            throw new IllegalStateException("Changing Realm data can only be done from inside a write transaction.");
        }
    }

    public static native void nativeAdd(long var0, long var2);

    public static native void nativeClear(long var0);

    private native long nativeFind(long var1, long var3);

    private static native long nativeGetFinalizerPtr();

    private native long nativeGetTargetRowIndex(long var1, long var3);

    private native long nativeGetTargetTable(long var1);

    private native void nativeInsert(long var1, long var3, long var5);

    private native boolean nativeIsAttached(long var1);

    private native boolean nativeIsEmpty(long var1);

    private native void nativeMove(long var1, long var3, long var5);

    private native void nativeRemove(long var1, long var3);

    private native void nativeRemoveAllTargetRows(long var1);

    private native void nativeRemoveTargetRow(long var1, long var3);

    private native void nativeSet(long var1, long var3, long var5);

    private native long nativeSize(long var1);

    public void add(long l) {
        this.checkImmutable();
        LinkView.nativeAdd(this.nativePtr, l);
    }

    public void clear() {
        this.checkImmutable();
        LinkView.nativeClear(this.nativePtr);
    }

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    public long getTargetRowIndex(long l) {
        return this.nativeGetTargetRowIndex(this.nativePtr, l);
    }

    public Table getTargetTable() {
        long l = this.nativeGetTargetTable(this.nativePtr);
        return new Table(this.parent, l);
    }

    public void insert(long l, long l2) {
        this.checkImmutable();
        this.nativeInsert(this.nativePtr, l, l2);
    }

    public boolean isAttached() {
        return this.nativeIsAttached(this.nativePtr);
    }

    public boolean isEmpty() {
        return this.nativeIsEmpty(this.nativePtr);
    }

    native long nativeGetRow(long var1, long var3);

    protected native long nativeWhere(long var1);

    public void remove(long l) {
        this.checkImmutable();
        this.nativeRemove(this.nativePtr, l);
    }

    public void set(long l, long l2) {
        this.checkImmutable();
        this.nativeSet(this.nativePtr, l, l2);
    }

    public long size() {
        return this.nativeSize(this.nativePtr);
    }
}

