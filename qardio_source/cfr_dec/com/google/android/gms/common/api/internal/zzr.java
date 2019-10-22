/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 */
package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import com.google.android.gms.common.api.internal.zzby;
import com.google.android.gms.common.api.internal.zzo;
import com.google.android.gms.common.api.internal.zzq;

final class zzr
extends zzby {
    private /* synthetic */ Dialog zzfor;
    private /* synthetic */ zzq zzfos;

    zzr(zzq zzq2, Dialog dialog) {
        this.zzfos = zzq2;
        this.zzfor = dialog;
    }

    @Override
    public final void zzahg() {
        this.zzfos.zzfoq.zzahd();
        if (this.zzfor.isShowing()) {
            this.zzfor.dismiss();
        }
    }
}

