/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzap;
import com.google.android.gms.common.api.internal.zzay;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.internal.zzan;
import java.util.ArrayList;
import java.util.Set;

final class zzau
extends zzay {
    private /* synthetic */ zzao zzfrl;
    private final ArrayList<Api.zze> zzfrr;

    public zzau(ArrayList<Api.zze> arrayList) {
        this.zzfrl = var1_1;
        super(var1_1, null);
        this.zzfrr = arrayList;
    }

    @Override
    public final void zzaib() {
        zzao.zzd((zzao)this.zzfrl).zzfpi.zzfsc = zzao.zzg(this.zzfrl);
        ArrayList<Api.zze> arrayList = this.zzfrr;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            Api.zze zze2 = arrayList.get(i);
            zze2.zza(zzao.zzh(this.zzfrl), zzao.zzd((zzao)this.zzfrl).zzfpi.zzfsc);
        }
    }
}

