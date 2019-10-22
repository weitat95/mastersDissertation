/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzfc;

public class Container {
    private final String zzkdd;
    private zzfc zzkdf;

    private final zzfc zzbdu() {
        synchronized (this) {
            zzfc zzfc2 = this.zzkdf;
            return zzfc2;
        }
    }

    public String getContainerId() {
        return this.zzkdd;
    }

    final void release() {
        this.zzkdf = null;
    }

    public final void zzld(String string2) {
        this.zzbdu().zzld(string2);
    }
}

