/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.content.Intent
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzr;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final String mName;
    private final zza<?, O> zzfls;
    private final zzh<?, O> zzflt;
    private final zzf<?> zzflu;
    private final zzi<?> zzflv;

    public <C extends zze> Api(String string2, zza<C, O> zza2, zzf<C> zzf2) {
        zzbq.checkNotNull(zza2, "Cannot construct an Api with a null ClientBuilder");
        zzbq.checkNotNull(zzf2, "Cannot construct an Api with a null ClientKey");
        this.mName = string2;
        this.zzfls = zza2;
        this.zzflt = null;
        this.zzflu = zzf2;
        this.zzflv = null;
    }

    public final String getName() {
        return this.mName;
    }

    public final zzd<?, O> zzagd() {
        return this.zzfls;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final zza<?, O> zzage() {
        boolean bl = this.zzfls != null;
        zzbq.zza(bl, "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.zzfls;
    }

    public final zzc<?> zzagf() {
        if (this.zzflu != null) {
            return this.zzflu;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }

    public static interface ApiOptions {

        public static interface HasAccountOptions
        extends HasOptions,
        NotRequiredOptions {
            public Account getAccount();
        }

        public static interface HasGoogleSignInAccountOptions
        extends HasOptions {
            public GoogleSignInAccount getGoogleSignInAccount();
        }

        public static interface HasOptions
        extends ApiOptions {
        }

        public static interface NotRequiredOptions
        extends ApiOptions {
        }

        public static interface Optional
        extends HasOptions,
        NotRequiredOptions {
        }

    }

    public static abstract class zza<T extends zze, O>
    extends zzd<T, O> {
        public abstract T zza(Context var1, Looper var2, zzr var3, O var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6);
    }

    public static interface zzb {
    }

    public static class zzc<C extends zzb> {
    }

    public static class zzd<T extends zzb, O> {
        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public List<Scope> zzr(O o) {
            return Collections.emptyList();
        }
    }

    public static interface zze
    extends zzb {
        public void disconnect();

        public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

        public Intent getSignInIntent();

        public boolean isConnected();

        public boolean isConnecting();

        public void zza(zzan var1, Set<Scope> var2);

        public void zza(zzj var1);

        public void zza(zzp var1);

        public boolean zzaay();

        public boolean zzabj();

        public boolean zzagg();

        public IBinder zzagh();

        public String zzagi();
    }

    public static final class zzf<C extends zze>
    extends zzc<C> {
    }

    public static interface zzg<T extends IInterface>
    extends zzb {
    }

    public static class zzh<T extends zzg, O>
    extends zzd<T, O> {
    }

    public static final class zzi<C extends zzg>
    extends zzc<C> {
    }

}

