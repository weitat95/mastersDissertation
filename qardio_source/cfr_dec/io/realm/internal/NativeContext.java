/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.internal.FinalizerRunnable;
import io.realm.internal.NativeObject;
import io.realm.internal.NativeObjectReference;
import java.lang.ref.ReferenceQueue;

public class NativeContext {
    static final NativeContext dummyContext;
    private static final Thread finalizingThread;
    private static final ReferenceQueue<NativeObject> referenceQueue;

    static {
        referenceQueue = new ReferenceQueue();
        finalizingThread = new Thread(new FinalizerRunnable(referenceQueue));
        dummyContext = new NativeContext();
        finalizingThread.setName("RealmFinalizingDaemon");
        finalizingThread.start();
    }

    void addReference(NativeObject nativeObject) {
        new NativeObjectReference(this, nativeObject, referenceQueue);
    }
}

