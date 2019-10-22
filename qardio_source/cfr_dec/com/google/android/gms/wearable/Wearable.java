/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.wearable;

import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.internal.zzaj;
import com.google.android.gms.wearable.internal.zzbv;
import com.google.android.gms.wearable.internal.zzbw;
import com.google.android.gms.wearable.internal.zzeu;
import com.google.android.gms.wearable.internal.zzfg;
import com.google.android.gms.wearable.internal.zzgi;
import com.google.android.gms.wearable.internal.zzh;
import com.google.android.gms.wearable.internal.zzhg;
import com.google.android.gms.wearable.internal.zzhq;
import com.google.android.gms.wearable.internal.zzk;
import com.google.android.gms.wearable.internal.zzo;
import com.google.android.gms.wearable.zza;
import com.google.android.gms.wearable.zzc;
import com.google.android.gms.wearable.zzf;
import com.google.android.gms.wearable.zzi;
import com.google.android.gms.wearable.zzj;
import com.google.android.gms.wearable.zzu;

public class Wearable {
    @Deprecated
    public static final Api<WearableOptions> API;
    @Deprecated
    public static final CapabilityApi CapabilityApi;
    @Deprecated
    public static final ChannelApi ChannelApi;
    @Deprecated
    public static final DataApi DataApi;
    @Deprecated
    public static final MessageApi MessageApi;
    @Deprecated
    public static final NodeApi NodeApi;
    private static final Api.zzf<zzhg> zzebf;
    private static final Api.zza<zzhg, WearableOptions> zzebg;
    @Deprecated
    private static zzc zzlgy;
    @Deprecated
    private static zza zzlgz;
    @Deprecated
    private static zzf zzlha;
    @Deprecated
    private static zzi zzlhb;
    @Deprecated
    private static zzu zzlhc;

    static {
        DataApi = new zzbw();
        CapabilityApi = new zzo();
        MessageApi = new zzeu();
        NodeApi = new zzfg();
        ChannelApi = new zzaj();
        zzlgy = new zzk();
        zzlgz = new zzh();
        zzlha = new zzbv();
        zzlhb = new zzgi();
        zzlhc = new zzhq();
        zzebf = new Api.zzf();
        zzebg = new zzj();
        API = new Api<WearableOptions>("Wearable.API", zzebg, zzebf);
    }

    public static final class WearableOptions
    implements Api.ApiOptions.Optional {
        private final Looper zzfml;

        private WearableOptions(Builder builder) {
            this.zzfml = builder.zzfml;
        }

        /* synthetic */ WearableOptions(Builder builder, zzj zzj2) {
            this(builder);
        }

        public static class Builder {
            private Looper zzfml;
        }

    }

}

