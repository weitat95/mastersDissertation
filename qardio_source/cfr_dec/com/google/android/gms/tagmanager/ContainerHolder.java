/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;

public interface ContainerHolder
extends Releasable,
Result {

    public static interface ContainerAvailableListener {
        public void onContainerAvailable(ContainerHolder var1, String var2);
    }

}

