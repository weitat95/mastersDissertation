/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzarx;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

final class zzary {
    private int zzdyk;
    private ByteArrayOutputStream zzdyl = new ByteArrayOutputStream();
    private /* synthetic */ zzarx zzdym;

    public zzary(zzarx zzarx2) {
        this.zzdym = zzarx2;
    }

    public final byte[] getPayload() {
        return this.zzdyl.toByteArray();
    }

    public final boolean zze(zzarq zzarq2) {
        zzbq.checkNotNull(zzarq2);
        if (this.zzdyk + 1 > zzard.zzyv()) {
            return false;
        }
        byte[] arrby = this.zzdym.zza(zzarq2, false);
        if (arrby == null) {
            this.zzdym.zzwt().zza(zzarq2, "Error formatting hit");
            return true;
        }
        int n = (arrby = arrby.getBytes()).length;
        if (n > zzard.zzyr()) {
            this.zzdym.zzwt().zza(zzarq2, "Hit size exceeds the maximum size limit");
            return true;
        }
        int n2 = n;
        if (this.zzdyl.size() > 0) {
            n2 = n + 1;
        }
        if (this.zzdyl.size() + n2 > zzarl.zzdww.get()) {
            return false;
        }
        try {
            if (this.zzdyl.size() > 0) {
                this.zzdyl.write(zzarx.zzzu());
            }
            this.zzdyl.write(arrby);
            ++this.zzdyk;
            return true;
        }
        catch (IOException iOException) {
            this.zzdym.zze("Failed to write payload when batching hits", iOException);
            return true;
        }
    }

    public final int zzzv() {
        return this.zzdyk;
    }
}

