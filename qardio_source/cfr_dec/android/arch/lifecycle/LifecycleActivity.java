/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v4.app.FragmentActivity;

public class LifecycleActivity
extends FragmentActivity
implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }
}

