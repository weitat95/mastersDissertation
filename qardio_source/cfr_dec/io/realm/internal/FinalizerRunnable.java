/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.internal.NativeObject;
import io.realm.internal.NativeObjectReference;
import io.realm.log.RealmLog;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

class FinalizerRunnable
implements Runnable {
    private final ReferenceQueue<NativeObject> referenceQueue;

    FinalizerRunnable(ReferenceQueue<NativeObject> referenceQueue) {
        this.referenceQueue = referenceQueue;
    }

    @Override
    public void run() {
        try {
            do {
                ((NativeObjectReference)this.referenceQueue.remove()).cleanup();
            } while (true);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            RealmLog.fatal("The FinalizerRunnable thread has been interrupted. Native resources cannot be freed anymore", new Object[0]);
            return;
        }
    }
}

