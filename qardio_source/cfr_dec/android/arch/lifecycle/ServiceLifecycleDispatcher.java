/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Handler;

public class ServiceLifecycleDispatcher {
    private final Handler mHandler;
    private DispatchRunnable mLastDispatchRunnable;
    private final LifecycleRegistry mRegistry;

    public ServiceLifecycleDispatcher(LifecycleOwner lifecycleOwner) {
        this.mRegistry = new LifecycleRegistry(lifecycleOwner);
        this.mHandler = new Handler();
    }

    private void postDispatchRunnable(Lifecycle.Event event) {
        if (this.mLastDispatchRunnable != null) {
            this.mLastDispatchRunnable.run();
        }
        this.mLastDispatchRunnable = new DispatchRunnable(this.mRegistry, event);
        this.mHandler.postAtFrontOfQueue((Runnable)this.mLastDispatchRunnable);
    }

    public Lifecycle getLifecycle() {
        return this.mRegistry;
    }

    public void onServicePreSuperOnBind() {
        this.postDispatchRunnable(Lifecycle.Event.ON_START);
    }

    public void onServicePreSuperOnCreate() {
        this.postDispatchRunnable(Lifecycle.Event.ON_CREATE);
    }

    public void onServicePreSuperOnDestroy() {
        this.postDispatchRunnable(Lifecycle.Event.ON_STOP);
        this.postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
    }

    public void onServicePreSuperOnStart() {
        this.postDispatchRunnable(Lifecycle.Event.ON_START);
    }

    static class DispatchRunnable
    implements Runnable {
        final Lifecycle.Event mEvent;
        private final LifecycleRegistry mRegistry;
        private boolean mWasExecuted = false;

        DispatchRunnable(LifecycleRegistry lifecycleRegistry, Lifecycle.Event event) {
            this.mRegistry = lifecycleRegistry;
            this.mEvent = event;
        }

        @Override
        public void run() {
            if (!this.mWasExecuted) {
                this.mRegistry.handleLifecycleEvent(this.mEvent);
                this.mWasExecuted = true;
            }
        }
    }

}

