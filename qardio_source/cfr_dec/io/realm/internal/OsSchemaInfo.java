/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.OsObjectSchemaInfo;
import java.util.Collection;
import java.util.Iterator;

public class OsSchemaInfo
implements NativeObject {
    private static final long nativeFinalizerPtr = OsSchemaInfo.nativeGetFinalizerPtr();
    private long nativePtr;

    public OsSchemaInfo(Collection<OsObjectSchemaInfo> object) {
        long[] arrl = new long[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrl[n] = ((OsObjectSchemaInfo)object.next()).getNativePtr();
            ++n;
        }
        this.nativePtr = OsSchemaInfo.nativeCreateFromList(arrl);
        NativeContext.dummyContext.addReference(this);
    }

    private static native long nativeCreateFromList(long[] var0);

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

