/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

final class NativeObjectReference
extends PhantomReference<NativeObject> {
    private static ReferencePool referencePool = new ReferencePool();
    private final NativeContext context;
    private final long nativeFinalizerPtr;
    private final long nativePtr;
    private NativeObjectReference next;
    private NativeObjectReference prev;

    NativeObjectReference(NativeContext nativeContext, NativeObject nativeObject, ReferenceQueue<? super NativeObject> referenceQueue) {
        super(nativeObject, referenceQueue);
        this.nativePtr = nativeObject.getNativePtr();
        this.nativeFinalizerPtr = nativeObject.getNativeFinalizerPtr();
        this.context = nativeContext;
        referencePool.add(this);
    }

    private static native void nativeCleanUp(long var0, long var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void cleanup() {
        NativeContext nativeContext = this.context;
        synchronized (nativeContext) {
            NativeObjectReference.nativeCleanUp(this.nativeFinalizerPtr, this.nativePtr);
        }
        referencePool.remove(this);
    }

    private static class ReferencePool {
        NativeObjectReference head;

        private ReferencePool() {
        }

        void add(NativeObjectReference nativeObjectReference) {
            synchronized (this) {
                nativeObjectReference.prev = null;
                nativeObjectReference.next = this.head;
                if (this.head != null) {
                    this.head.prev = nativeObjectReference;
                }
                this.head = nativeObjectReference;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void remove(NativeObjectReference nativeObjectReference) {
            synchronized (this) {
                NativeObjectReference nativeObjectReference2 = nativeObjectReference.next;
                NativeObjectReference nativeObjectReference3 = nativeObjectReference.prev;
                nativeObjectReference.next = null;
                nativeObjectReference.prev = null;
                if (nativeObjectReference3 != null) {
                    nativeObjectReference3.next = nativeObjectReference2;
                } else {
                    this.head = nativeObjectReference2;
                }
                if (nativeObjectReference2 != null) {
                    nativeObjectReference2.prev = nativeObjectReference3;
                }
                return;
            }
        }
    }

}

