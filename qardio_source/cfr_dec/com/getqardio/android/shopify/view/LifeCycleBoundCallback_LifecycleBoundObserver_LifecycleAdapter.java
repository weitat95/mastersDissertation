/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback;

public class LifeCycleBoundCallback_LifecycleBoundObserver_LifecycleAdapter
implements GenericLifecycleObserver {
    final LifeCycleBoundCallback.LifecycleBoundObserver mReceiver;

    LifeCycleBoundCallback_LifecycleBoundObserver_LifecycleAdapter(LifeCycleBoundCallback.LifecycleBoundObserver lifecycleBoundObserver) {
        this.mReceiver = lifecycleBoundObserver;
    }

    public Object getReceiver() {
        return this.mReceiver;
    }

    @Override
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        this.mReceiver.onStateChange(lifecycleOwner, event);
    }
}

