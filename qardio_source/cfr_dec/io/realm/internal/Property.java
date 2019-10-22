/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;

public class Property
implements NativeObject {
    private static final long nativeFinalizerPtr = Property.nativeGetFinalizerPtr();
    private long nativePtr;

    Property(String string2, RealmFieldType realmFieldType, String string3) {
        this.nativePtr = Property.nativeCreateProperty(string2, realmFieldType.getNativeValue(), string3);
        NativeContext.dummyContext.addReference(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    Property(String string2, RealmFieldType realmFieldType, boolean bl, boolean bl2, boolean bl3) {
        int n = realmFieldType.getNativeValue();
        bl3 = !bl3;
        this.nativePtr = Property.nativeCreateProperty(string2, n, bl, bl2, bl3);
        NativeContext.dummyContext.addReference(this);
    }

    private static native long nativeCreateProperty(String var0, int var1, String var2);

    private static native long nativeCreateProperty(String var0, int var1, boolean var2, boolean var3, boolean var4);

    private static native long nativeGetFinalizerPtr();

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }
}

