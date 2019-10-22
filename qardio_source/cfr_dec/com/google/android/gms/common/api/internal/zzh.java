/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbg;
import java.util.Arrays;

public final class zzh<O extends Api.ApiOptions> {
    private final Api<O> zzfin;
    private final O zzfme;
    private final boolean zzfnv;
    private final int zzfnw;

    private zzh(Api<O> api) {
        this.zzfnv = true;
        this.zzfin = api;
        this.zzfme = null;
        this.zzfnw = System.identityHashCode(this);
    }

    private zzh(Api<O> api, O o) {
        this.zzfnv = false;
        this.zzfin = api;
        this.zzfme = o;
        this.zzfnw = Arrays.hashCode(new Object[]{this.zzfin, this.zzfme});
    }

    public static <O extends Api.ApiOptions> zzh<O> zza(Api<O> api, O o) {
        return new zzh<O>(api, o);
    }

    public static <O extends Api.ApiOptions> zzh<O> zzb(Api<O> api) {
        return new zzh<O>(api);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof zzh)) {
                    return false;
                }
                object = (zzh)object;
                if (this.zzfnv || ((zzh)object).zzfnv || !zzbg.equal(this.zzfin, ((zzh)object).zzfin) || !zzbg.equal(this.zzfme, ((zzh)object).zzfme)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.zzfnw;
    }

    public final String zzagy() {
        return this.zzfin.getName();
    }
}

