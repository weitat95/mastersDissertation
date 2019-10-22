/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzchz;
import com.google.android.gms.internal.zzcia;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzclf;

final class zzclg
extends zzcgs {
    private /* synthetic */ zzclf zzjjf;

    zzclg(zzclf zzclf2, zzcim zzcim2) {
        this.zzjjf = zzclf2;
        super(zzcim2);
    }

    @Override
    public final void run() {
        zzclf zzclf2 = this.zzjjf;
        ((zzcjk)zzclf2).zzve();
        long l = ((zzcjk)zzclf2).zzws().elapsedRealtime();
        ((zzcjk)zzclf2).zzawy().zzazj().zzj("Session started, time", l);
        zzclf2.zzawz().zzjdg.set(false);
        ((zzcjk)zzclf2).zzawm().zzc("auto", "_s", new Bundle());
        zzclf2.zzawz().zzjdh.set(((zzcjk)zzclf2).zzws().currentTimeMillis());
    }
}

