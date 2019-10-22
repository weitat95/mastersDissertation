/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentCallbacks2
 *  android.content.res.Configuration
 */
package com.google.android.gms.tagmanager;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import com.google.android.gms.tagmanager.TagManager;

final class zzgd
implements ComponentCallbacks2 {
    private /* synthetic */ TagManager zzkke;

    zzgd(TagManager tagManager) {
        this.zzkke = tagManager;
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }

    public final void onTrimMemory(int n) {
        if (n == 20) {
            this.zzkke.dispatch();
        }
    }
}

