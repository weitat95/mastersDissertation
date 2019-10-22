/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.internal.zzceb;
import com.google.android.gms.internal.zzceq;
import com.google.android.gms.internal.zzcfk;
import com.google.android.gms.internal.zzcfv;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.SettingsApi;
import com.google.android.gms.location.zzy;

public class LocationServices {
    public static final Api<Object> API;
    @Deprecated
    public static final FusedLocationProviderApi FusedLocationApi;
    @Deprecated
    public static final GeofencingApi GeofencingApi;
    @Deprecated
    public static final SettingsApi SettingsApi;
    private static final Api.zzf<zzcfk> zzebf;
    private static final Api.zza<zzcfk, Object> zzebg;

    static {
        zzebf = new Api.zzf();
        zzebg = new zzy();
        API = new Api<Object>("LocationServices.API", zzebg, zzebf);
        FusedLocationApi = new zzceb();
        GeofencingApi = new zzceq();
        SettingsApi = new zzcfv();
    }

    public static abstract class zza<R extends Result>
    extends zzm<R, zzcfk> {
        public zza(GoogleApiClient googleApiClient) {
            super(API, googleApiClient);
        }
    }

}

