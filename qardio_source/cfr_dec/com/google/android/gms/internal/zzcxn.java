/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzz;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import com.google.android.gms.internal.zzcxj;
import com.google.android.gms.internal.zzcxl;
import com.google.android.gms.internal.zzcxm;
import com.google.android.gms.internal.zzcxo;
import com.google.android.gms.internal.zzcxq;

public final class zzcxn
extends zzab<zzcxl>
implements zzcxd {
    private final zzr zzfpx;
    private Integer zzfzj;
    private final boolean zzkbz;
    private final Bundle zzkca;

    private zzcxn(Context context, Looper looper, boolean bl, zzr zzr2, Bundle bundle, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, zzr2, connectionCallbacks, onConnectionFailedListener);
        this.zzkbz = true;
        this.zzfpx = zzr2;
        this.zzkca = bundle;
        this.zzfzj = zzr2.zzalc();
    }

    public zzcxn(Context context, Looper looper, boolean bl, zzr zzr2, zzcxe zzcxe2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, true, zzr2, zzcxn.zza(zzr2), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle zza(zzr zzr2) {
        zzcxe zzcxe2 = zzr2.zzalb();
        Integer n = zzr2.zzalc();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", (Parcelable)zzr2.getAccount());
        if (n != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", n.intValue());
        }
        if (zzcxe2 != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzcxe2.zzbdc());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzcxe2.isIdTokenRequested());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzcxe2.getServerClientId());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzcxe2.zzbdd());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzcxe2.zzbde());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzcxe2.zzbdf());
            if (zzcxe2.zzbdg() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzcxe2.zzbdg().longValue());
            }
            if (zzcxe2.zzbdh() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzcxe2.zzbdh().longValue());
            }
        }
        return bundle;
    }

    @Override
    public final void connect() {
        this.zza(new zzm(this));
    }

    @Override
    public final void zza(zzan zzan2, boolean bl) {
        try {
            ((zzcxl)this.zzakn()).zza(zzan2, this.zzfzj, bl);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when saveDefaultAccount is called");
            return;
        }
    }

    @Override
    public final void zza(zzcxj zzcxj2) {
        zzbfm zzbfm2;
        Account account;
        zzbq.checkNotNull(zzcxj2, "Expecting a valid ISignInCallbacks");
        try {
            account = this.zzfpx.zzakt();
            zzbfm2 = null;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when signIn is called");
            try {
                zzcxj2.zzb(new zzcxq(8));
                return;
            }
            catch (RemoteException remoteException2) {
                Log.wtf((String)"SignInClientImpl", (String)"ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", (Throwable)remoteException);
                return;
            }
        }
        if ("<<default account>>".equals(account.name)) {
            zzbfm2 = zzz.zzbt(this.getContext()).zzabt();
        }
        zzbfm2 = new zzbr(account, this.zzfzj, (GoogleSignInAccount)zzbfm2);
        ((zzcxl)this.zzakn()).zza(new zzcxo((zzbr)zzbfm2), zzcxj2);
    }

    @Override
    protected final Bundle zzaap() {
        String string2 = this.zzfpx.zzaky();
        if (!this.getContext().getPackageName().equals(string2)) {
            this.zzkca.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzfpx.zzaky());
        }
        return this.zzkca;
    }

    @Override
    public final boolean zzaay() {
        return this.zzkbz;
    }

    @Override
    public final void zzbdb() {
        try {
            ((zzcxl)this.zzakn()).zzeh(this.zzfzj);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when clearAccountFromSessionStore is called");
            return;
        }
    }

    @Override
    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        if (iInterface instanceof zzcxl) {
            return (zzcxl)iInterface;
        }
        return new zzcxm(iBinder);
    }

    @Override
    protected final String zzhi() {
        return "com.google.android.gms.signin.service.START";
    }

    @Override
    protected final String zzhj() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }
}

