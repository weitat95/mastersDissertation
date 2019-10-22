/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.support.v4.util.ArraySet;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.internal.zzcxe;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public final class zzs {
    private String zzebs;
    private Account zzebz;
    private int zzfmq = 0;
    private String zzfms;
    private zzcxe zzfzi = zzcxe.zzkbs;
    private ArraySet<Scope> zzfzk;

    public final zzr zzald() {
        return new zzr(this.zzebz, this.zzfzk, null, 0, null, this.zzebs, this.zzfms, this.zzfzi);
    }

    public final zzs zze(Account account) {
        this.zzebz = account;
        return this;
    }

    public final zzs zze(Collection<Scope> collection) {
        if (this.zzfzk == null) {
            this.zzfzk = new ArraySet();
        }
        this.zzfzk.addAll(collection);
        return this;
    }

    public final zzs zzgf(String string2) {
        this.zzebs = string2;
        return this;
    }

    public final zzs zzgg(String string2) {
        this.zzfms = string2;
        return this;
    }
}

