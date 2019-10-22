/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.OrderedCollectionChangeSet;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;

public class CollectionChangeSet
implements OrderedCollectionChangeSet,
NativeObject {
    private static long finalizerPtr = CollectionChangeSet.nativeGetFinalizerPtr();
    private final long nativePtr;

    public CollectionChangeSet(long l) {
        this.nativePtr = l;
        NativeContext.dummyContext.addReference(this);
    }

    private static native long nativeGetFinalizerPtr();

    private static native int[] nativeGetIndices(long var0, int var2);

    private static native int[] nativeGetRanges(long var0, int var2);

    @Override
    public long getNativeFinalizerPtr() {
        return finalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }
}

