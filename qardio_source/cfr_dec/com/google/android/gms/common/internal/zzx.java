/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.google.android.gms.common.internal.zzv;

final class zzx
extends zzv {
    private /* synthetic */ Fragment val$fragment;
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ int val$requestCode;

    zzx(Intent intent, Fragment fragment, int n) {
        this.val$intent = intent;
        this.val$fragment = fragment;
        this.val$requestCode = n;
    }

    @Override
    public final void zzale() {
        if (this.val$intent != null) {
            this.val$fragment.startActivityForResult(this.val$intent, this.val$requestCode);
        }
    }
}

