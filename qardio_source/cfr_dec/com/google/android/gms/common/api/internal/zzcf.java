/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.common.api.internal.LifecycleCallback;

public interface zzcf {
    public void startActivityForResult(Intent var1, int var2);

    public <T extends LifecycleCallback> T zza(String var1, Class<T> var2);

    public void zza(String var1, LifecycleCallback var2);

    public Activity zzajn();
}

