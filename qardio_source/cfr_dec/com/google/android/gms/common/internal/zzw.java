/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.common.internal.zzv;

final class zzw
extends zzv {
    private /* synthetic */ Activity val$activity;
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ int val$requestCode;

    zzw(Intent intent, Activity activity, int n) {
        this.val$intent = intent;
        this.val$activity = activity;
        this.val$requestCode = n;
    }

    @Override
    public final void zzale() {
        if (this.val$intent != null) {
            this.val$activity.startActivityForResult(this.val$intent, this.val$requestCode);
        }
    }
}

