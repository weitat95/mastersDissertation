/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzf;
import java.util.Iterator;
import java.util.Set;

public abstract class zzab<T extends IInterface>
extends zzd<T>
implements Api.zze,
zzaf {
    private final Account zzebz;
    private final Set<Scope> zzehs;
    private final zzr zzfpx;

    protected zzab(Context context, Looper looper, int n, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzag.zzco(context), GoogleApiAvailability.getInstance(), n, zzr2, zzbq.checkNotNull(connectionCallbacks), zzbq.checkNotNull(onConnectionFailedListener));
    }

    /*
     * Enabled aggressive block sorting
     */
    private zzab(Context object, Looper object2, zzag iterator, GoogleApiAvailability googleApiAvailability, int n, zzr zzr2, GoogleApiClient.ConnectionCallbacks object3, GoogleApiClient.OnConnectionFailedListener object4) {
        object3 = object3 == null ? null : new zzac((GoogleApiClient.ConnectionCallbacks)object3);
        object4 = object4 == null ? null : new zzad((GoogleApiClient.OnConnectionFailedListener)object4);
        super((Context)object, (Looper)object2, (zzag)((Object)iterator), googleApiAvailability, n, (com.google.android.gms.common.internal.zzf)object3, (zzg)object4, zzr2.zzakz());
        this.zzfpx = zzr2;
        this.zzebz = zzr2.getAccount();
        object = zzr2.zzakw();
        object2 = this.zzb((Set<Scope>)object);
        iterator = object2.iterator();
        do {
            if (iterator.hasNext()) continue;
            this.zzehs = object2;
            return;
        } while (object.contains((Scope)iterator.next()));
        throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
    }

    @Override
    public final Account getAccount() {
        return this.zzebz;
    }

    @Override
    public zzc[] zzakl() {
        return new zzc[0];
    }

    @Override
    protected final Set<Scope> zzakp() {
        return this.zzehs;
    }

    protected Set<Scope> zzb(Set<Scope> set) {
        return set;
    }
}

