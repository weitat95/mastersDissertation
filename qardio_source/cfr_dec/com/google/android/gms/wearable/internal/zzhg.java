/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.zza;
import com.google.android.gms.wearable.internal.zzdd;
import com.google.android.gms.wearable.internal.zzek;
import com.google.android.gms.wearable.internal.zzep;
import com.google.android.gms.wearable.internal.zzeq;
import com.google.android.gms.wearable.internal.zzer;
import com.google.android.gms.wearable.internal.zzfu;
import com.google.android.gms.wearable.internal.zzhb;
import com.google.android.gms.wearable.internal.zzhh;
import com.google.android.gms.wearable.internal.zzhp;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public final class zzhg
extends zzab<zzep> {
    private final ExecutorService zzieo;
    private final zzer<Object> zzllh = new zzer();
    private final zzer<Object> zzlli = new zzer();
    private final zzer<ChannelApi.ChannelListener> zzllj = new zzer();
    private final zzer<DataApi.DataListener> zzllk = new zzer();
    private final zzer<MessageApi.MessageListener> zzlll = new zzer();
    private final zzer<Object> zzllm = new zzer();
    private final zzer<Object> zzlln = new zzer();
    private final zzer<CapabilityApi.CapabilityListener> zzllo = new zzer();
    private final zzhp zzllp;

    public zzhg(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzr zzr2) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, zzr2, Executors.newCachedThreadPool(), zzhp.zzep(context));
    }

    private zzhg(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzr zzr2, ExecutorService executorService, zzhp zzhp2) {
        super(context, looper, 14, zzr2, connectionCallbacks, onConnectionFailedListener);
        this.zzieo = zzbq.checkNotNull(executorService);
        this.zzllp = zzhp2;
    }

    @Override
    protected final void zza(int n, IBinder iBinder, Bundle bundle, int n2) {
        if (Log.isLoggable((String)"WearableClient", (int)2)) {
            Log.d((String)"WearableClient", (String)new StringBuilder(41).append("onPostInitHandler: statusCode ").append(n).toString());
        }
        if (n == 0) {
            this.zzllh.zzbr(iBinder);
            this.zzlli.zzbr(iBinder);
            this.zzllj.zzbr(iBinder);
            this.zzllk.zzbr(iBinder);
            this.zzlll.zzbr(iBinder);
            this.zzllm.zzbr(iBinder);
            this.zzlln.zzbr(iBinder);
            this.zzllo.zzbr(iBinder);
        }
        super.zza(n, iBinder, bundle, n2);
    }

    public final void zza(zzn<DataApi.DataItemResult> object, PutDataRequest object2) throws RemoteException {
        Object object3;
        Object object4 = ((PutDataRequest)object2).getAssets().entrySet().iterator();
        while (object4.hasNext()) {
            object3 = object4.next().getValue();
            if (((Asset)object3).getData() != null || ((Asset)object3).getDigest() != null || ((Asset)object3).getFd() != null || ((Asset)object3).getUri() != null) continue;
            object = String.valueOf((Object)((PutDataRequest)object2).getUri());
            object2 = String.valueOf(object3);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(object).length() + 33 + String.valueOf(object2).length()).append("Put for ").append((String)object).append(" contains invalid asset: ").append((String)object2).toString());
        }
        object4 = PutDataRequest.zzs(((PutDataRequest)object2).getUri());
        ((PutDataRequest)object4).setData(((PutDataRequest)object2).getData());
        if (((PutDataRequest)object2).isUrgent()) {
            ((PutDataRequest)object4).setUrgent();
        }
        object3 = new ArrayList();
        for (Map.Entry<String, Asset> entry : ((PutDataRequest)object2).getAssets().entrySet()) {
            Object object5;
            Object object6 = entry.getValue();
            if (((Asset)object6).getData() != null) {
                try {
                    object5 = ParcelFileDescriptor.createPipe();
                }
                catch (IOException iOException) {
                    object2 = String.valueOf(object2);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(object2).length() + 60).append("Unable to create ParcelFileDescriptor for asset in request: ").append((String)object2).toString(), iOException);
                }
                if (Log.isLoggable((String)"WearableClient", (int)3)) {
                    String string2 = String.valueOf(object6);
                    String string3 = String.valueOf((Object)object5[0]);
                    String string4 = String.valueOf((Object)object5[1]);
                    Log.d((String)"WearableClient", (String)new StringBuilder(String.valueOf(string2).length() + 61 + String.valueOf(string3).length() + String.valueOf(string4).length()).append("processAssets: replacing data with FD in asset: ").append(string2).append(" read:").append(string3).append(" write:").append(string4).toString());
                }
                ((PutDataRequest)object4).putAsset(entry.getKey(), Asset.createFromFd(object5[0]));
                object6 = new FutureTask<Boolean>(new zzhh(this, object5[1], ((Asset)object6).getData()));
                object3.add(object6);
                this.zzieo.submit((Runnable)object6);
                continue;
            }
            if (((Asset)object6).getUri() != null) {
                try {
                    object5 = Asset.createFromFd(this.getContext().getContentResolver().openFileDescriptor(((Asset)object6).getUri(), "r"));
                    ((PutDataRequest)object4).putAsset(entry.getKey(), (Asset)object5);
                    continue;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    ((zza)new zzhb((zzn<DataApi.DataItemResult>)object, (List<FutureTask<Boolean>>)object3)).zza(new zzfu(4005, null));
                    object = String.valueOf((Object)((Asset)object6).getUri());
                    Log.w((String)"WearableClient", (String)new StringBuilder(String.valueOf(object).length() + 28).append("Couldn't resolve asset URI: ").append((String)object).toString());
                    return;
                }
            }
            ((PutDataRequest)object4).putAsset(entry.getKey(), (Asset)object6);
        }
        ((zzep)this.zzakn()).zza((zzek)new zzhb((zzn<DataApi.DataItemResult>)object, (List<FutureTask<Boolean>>)object3), (PutDataRequest)object4);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(zzj zzj2) {
        int n = 0;
        if (!((zzd)this).zzagg()) {
            try {
                Bundle bundle = this.getContext().getPackageManager().getApplicationInfo((String)"com.google.android.wearable.app.cn", (int)128).metaData;
                if (bundle != null) {
                    n = bundle.getInt("com.google.android.wearable.api.version", 0);
                }
                if (n < zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                    int n2 = zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE;
                    Log.w((String)"WearableClient", (String)new StringBuilder(80).append("Android Wear out of date. Requires API version ").append(n2).append(" but found ").append(n).toString());
                    Context context = this.getContext();
                    Context context2 = this.getContext();
                    bundle = new Intent("com.google.android.wearable.app.cn.UPDATE_ANDROID_WEAR").setPackage("com.google.android.wearable.app.cn");
                    if (context2.getPackageManager().resolveActivity((Intent)bundle, 65536) == null) {
                        bundle = new Intent("android.intent.action.VIEW", Uri.parse((String)"market://details").buildUpon().appendQueryParameter("id", "com.google.android.wearable.app.cn").build());
                    }
                    this.zza(zzj2, 6, PendingIntent.getActivity((Context)context, (int)0, (Intent)bundle, (int)0));
                    return;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                this.zza(zzj2, 16, null);
                return;
            }
        }
        super.zza(zzj2);
    }

    @Override
    public final boolean zzagg() {
        return !this.zzllp.zznz("com.google.android.wearable.app.cn");
    }

    @Override
    protected final String zzakh() {
        if (this.zzllp.zznz("com.google.android.wearable.app.cn")) {
            return "com.google.android.wearable.app.cn";
        }
        return "com.google.android.gms";
    }

    @Override
    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
        if (iInterface instanceof zzep) {
            return (zzep)iInterface;
        }
        return new zzeq(iBinder);
    }

    @Override
    protected final String zzhi() {
        return "com.google.android.gms.wearable.BIND";
    }

    @Override
    protected final String zzhj() {
        return "com.google.android.gms.wearable.internal.IWearableService";
    }
}

