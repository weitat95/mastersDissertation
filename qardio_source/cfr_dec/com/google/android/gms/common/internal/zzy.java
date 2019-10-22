/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.internal.zzv;

final class zzy
extends zzv {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ zzcf zzfzm;

    zzy(Intent intent, zzcf zzcf2, int n) {
        this.val$intent = intent;
        this.zzfzm = zzcf2;
        this.val$requestCode = n;
    }

    @Override
    public final void zzale() {
        if (this.val$intent != null) {
            this.zzfzm.startActivityForResult(this.val$intent, this.val$requestCode);
        }
    }
}

