/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;

public interface LifecycleRegistryOwner
extends LifecycleOwner {
    @Override
    public LifecycleRegistry getLifecycle();
}

