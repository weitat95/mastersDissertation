/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.fitness;

import android.os.Build;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.BleApi;
import com.google.android.gms.fitness.ConfigApi;
import com.google.android.gms.fitness.GoalsApi;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.RecordingApi;
import com.google.android.gms.fitness.SensorsApi;
import com.google.android.gms.fitness.SessionsApi;
import com.google.android.gms.internal.zzbve;
import com.google.android.gms.internal.zzbvk;
import com.google.android.gms.internal.zzbvq;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbwb;
import com.google.android.gms.internal.zzbwh;
import com.google.android.gms.internal.zzbwn;
import com.google.android.gms.internal.zzbyi;
import com.google.android.gms.internal.zzbyq;
import com.google.android.gms.internal.zzbyv;
import com.google.android.gms.internal.zzbyy;
import com.google.android.gms.internal.zzbzi;
import com.google.android.gms.internal.zzbzp;
import com.google.android.gms.internal.zzbzt;
import com.google.android.gms.internal.zzcaf;

public class Fitness {
    @Deprecated
    public static final Void API;
    public static final Api<Object> BLE_API;
    public static final BleApi BleApi;
    public static final Api<Object> CONFIG_API;
    public static final ConfigApi ConfigApi;
    public static final Api<Object> GOALS_API;
    public static final GoalsApi GoalsApi;
    public static final Api<Object> HISTORY_API;
    public static final HistoryApi HistoryApi;
    public static final Api<Object> RECORDING_API;
    public static final RecordingApi RecordingApi;
    public static final Scope SCOPE_ACTIVITY_READ;
    public static final Scope SCOPE_ACTIVITY_READ_WRITE;
    public static final Scope SCOPE_BODY_READ;
    public static final Scope SCOPE_BODY_READ_WRITE;
    public static final Scope SCOPE_LOCATION_READ;
    public static final Scope SCOPE_LOCATION_READ_WRITE;
    public static final Scope SCOPE_NUTRITION_READ;
    public static final Scope SCOPE_NUTRITION_READ_WRITE;
    public static final Api<Object> SENSORS_API;
    public static final Api<Object> SESSIONS_API;
    public static final SensorsApi SensorsApi;
    public static final SessionsApi SessionsApi;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    static {
        void var0_1;
        API = null;
        SENSORS_API = zzbwh.API;
        SensorsApi = new zzbzp();
        RECORDING_API = zzbwb.API;
        RecordingApi = new zzbzi();
        SESSIONS_API = zzbwn.API;
        SessionsApi = new zzbzt();
        HISTORY_API = zzbvv.API;
        HistoryApi = new zzbyy();
        GOALS_API = zzbvq.API;
        GoalsApi = new zzbyv();
        CONFIG_API = zzbvk.API;
        ConfigApi = new zzbyq();
        BLE_API = zzbve.API;
        if (Build.VERSION.SDK_INT >= 18) {
            zzbyi zzbyi2 = new zzbyi();
        } else {
            zzcaf zzcaf2 = new zzcaf();
        }
        BleApi = var0_1;
        SCOPE_ACTIVITY_READ = new Scope("https://www.googleapis.com/auth/fitness.activity.read");
        SCOPE_ACTIVITY_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.activity.write");
        SCOPE_LOCATION_READ = new Scope("https://www.googleapis.com/auth/fitness.location.read");
        SCOPE_LOCATION_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.location.write");
        SCOPE_BODY_READ = new Scope("https://www.googleapis.com/auth/fitness.body.read");
        SCOPE_BODY_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.body.write");
        SCOPE_NUTRITION_READ = new Scope("https://www.googleapis.com/auth/fitness.nutrition.read");
        SCOPE_NUTRITION_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.nutrition.write");
    }
}

