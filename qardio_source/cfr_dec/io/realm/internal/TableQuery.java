/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.Case;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.Table;

public class TableQuery
implements NativeObject {
    private static final long nativeFinalizerPtr = TableQuery.nativeGetFinalizerPtr();
    private final NativeContext context;
    private final long nativePtr;
    private boolean queryValidated = true;
    private final Table table;

    public TableQuery(NativeContext nativeContext, Table table, long l) {
        this.context = nativeContext;
        this.table = table;
        this.nativePtr = l;
        nativeContext.addReference(this);
    }

    private native double nativeAverageDouble(long var1, long var3, long var5, long var7, long var9);

    private native double nativeAverageFloat(long var1, long var3, long var5, long var7, long var9);

    private native double nativeAverageInt(long var1, long var3, long var5, long var7, long var9);

    private native void nativeBeginsWith(long var1, long[] var3, long[] var4, String var5, boolean var6);

    private native void nativeBetween(long var1, long[] var3, double var4, double var6);

    private native void nativeBetween(long var1, long[] var3, float var4, float var5);

    private native void nativeBetween(long var1, long[] var3, long var4, long var6);

    private native void nativeBetweenTimestamp(long var1, long[] var3, long var4, long var6);

    private native void nativeContains(long var1, long[] var3, long[] var4, String var5, boolean var6);

    private native long nativeCount(long var1, long var3, long var5, long var7);

    private native void nativeEndGroup(long var1);

    private native void nativeEndsWith(long var1, long[] var3, long[] var4, String var5, boolean var6);

    private native void nativeEqual(long var1, long[] var3, long[] var4, double var5);

    private native void nativeEqual(long var1, long[] var3, long[] var4, float var5);

    private native void nativeEqual(long var1, long[] var3, long[] var4, long var5);

    private native void nativeEqual(long var1, long[] var3, long[] var4, String var5, boolean var6);

    private native void nativeEqual(long var1, long[] var3, long[] var4, boolean var5);

    private native void nativeEqual(long var1, long[] var3, long[] var4, byte[] var5);

    private native void nativeEqualTimestamp(long var1, long[] var3, long[] var4, long var5);

    private native long nativeFind(long var1, long var3);

    private native long nativeFindAll(long var1, long var3, long var5, long var7);

    private static native long nativeGetFinalizerPtr();

    private native void nativeGreater(long var1, long[] var3, long[] var4, double var5);

    private native void nativeGreater(long var1, long[] var3, long[] var4, float var5);

    private native void nativeGreater(long var1, long[] var3, long[] var4, long var5);

    private native void nativeGreaterEqual(long var1, long[] var3, long[] var4, double var5);

    private native void nativeGreaterEqual(long var1, long[] var3, long[] var4, float var5);

    private native void nativeGreaterEqual(long var1, long[] var3, long[] var4, long var5);

    private native void nativeGreaterEqualTimestamp(long var1, long[] var3, long[] var4, long var5);

    private native void nativeGreaterTimestamp(long var1, long[] var3, long[] var4, long var5);

    private native void nativeGroup(long var1);

    private native void nativeIsEmpty(long var1, long[] var3, long[] var4);

    private native void nativeIsNotEmpty(long var1, long[] var3, long[] var4);

    private native void nativeIsNotNull(long var1, long[] var3, long[] var4);

    private native void nativeIsNull(long var1, long[] var3, long[] var4);

    private native void nativeLess(long var1, long[] var3, long[] var4, double var5);

    private native void nativeLess(long var1, long[] var3, long[] var4, float var5);

    private native void nativeLess(long var1, long[] var3, long[] var4, long var5);

    private native void nativeLessEqual(long var1, long[] var3, long[] var4, double var5);

    private native void nativeLessEqual(long var1, long[] var3, long[] var4, float var5);

    private native void nativeLessEqual(long var1, long[] var3, long[] var4, long var5);

    private native void nativeLessEqualTimestamp(long var1, long[] var3, long[] var4, long var5);

    private native void nativeLessTimestamp(long var1, long[] var3, long[] var4, long var5);

    private native void nativeLike(long var1, long[] var3, long[] var4, String var5, boolean var6);

    private native Double nativeMaximumDouble(long var1, long var3, long var5, long var7, long var9);

    private native Float nativeMaximumFloat(long var1, long var3, long var5, long var7, long var9);

    private native Long nativeMaximumInt(long var1, long var3, long var5, long var7, long var9);

    private native Long nativeMaximumTimestamp(long var1, long var3, long var5, long var7, long var9);

    private native Double nativeMinimumDouble(long var1, long var3, long var5, long var7, long var9);

    private native Float nativeMinimumFloat(long var1, long var3, long var5, long var7, long var9);

    private native Long nativeMinimumInt(long var1, long var3, long var5, long var7, long var9);

    private native Long nativeMinimumTimestamp(long var1, long var3, long var5, long var7, long var9);

    private native void nativeNot(long var1);

    private native void nativeNotEqual(long var1, long[] var3, long[] var4, double var5);

    private native void nativeNotEqual(long var1, long[] var3, long[] var4, float var5);

    private native void nativeNotEqual(long var1, long[] var3, long[] var4, long var5);

    private native void nativeNotEqual(long var1, long[] var3, long[] var4, String var5, boolean var6);

    private native void nativeNotEqual(long var1, long[] var3, long[] var4, byte[] var5);

    private native void nativeNotEqualTimestamp(long var1, long[] var3, long[] var4, long var5);

    private native void nativeOr(long var1);

    private native long nativeRemove(long var1);

    private native double nativeSumDouble(long var1, long var3, long var5, long var7, long var9);

    private native double nativeSumFloat(long var1, long var3, long var5, long var7, long var9);

    private native long nativeSumInt(long var1, long var3, long var5, long var7, long var9);

    private native String nativeValidateQuery(long var1);

    public TableQuery equalTo(long[] arrl, long[] arrl2, long l) {
        this.nativeEqual(this.nativePtr, arrl, arrl2, l);
        this.queryValidated = false;
        return this;
    }

    public TableQuery equalTo(long[] arrl, long[] arrl2, String string2, Case case_) {
        this.nativeEqual(this.nativePtr, arrl, arrl2, string2, case_.getValue());
        this.queryValidated = false;
        return this;
    }

    public long find() {
        this.validateQuery();
        return this.nativeFind(this.nativePtr, 0L);
    }

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    public Table getTable() {
        return this.table;
    }

    public TableQuery isNotNull(long[] arrl, long[] arrl2) {
        this.nativeIsNotNull(this.nativePtr, arrl, arrl2);
        this.queryValidated = false;
        return this;
    }

    public TableQuery isNull(long[] arrl, long[] arrl2) {
        this.nativeIsNull(this.nativePtr, arrl, arrl2);
        this.queryValidated = false;
        return this;
    }

    public TableQuery notEqualTo(long[] arrl, long[] arrl2, long l) {
        this.nativeNotEqual(this.nativePtr, arrl, arrl2, l);
        this.queryValidated = false;
        return this;
    }

    void validateQuery() {
        String string2;
        block3: {
            block2: {
                if (this.queryValidated) break block2;
                string2 = this.nativeValidateQuery(this.nativePtr);
                if (!string2.equals("")) break block3;
                this.queryValidated = true;
            }
            return;
        }
        throw new UnsupportedOperationException(string2);
    }
}

