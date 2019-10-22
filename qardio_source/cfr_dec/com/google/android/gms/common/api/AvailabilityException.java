/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class AvailabilityException
extends Exception {
    private final ArrayMap<zzh<?>, ConnectionResult> zzflw;

    public AvailabilityException(ArrayMap<zzh<?>, ConnectionResult> arrayMap) {
        this.zzflw = arrayMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ConnectionResult getConnectionResult(GoogleApi<? extends Api.ApiOptions> zzh2) {
        boolean bl = this.zzflw.get(zzh2 = ((GoogleApi)((Object)zzh2)).zzagn()) != null;
        zzbq.checkArgument(bl, "The given API was not part of the availability request.");
        return (ConnectionResult)this.zzflw.get(zzh2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getMessage() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Object object = this.zzflw.keySet().iterator();
        boolean bl = true;
        while (object.hasNext()) {
            zzh<?> zzh2 = object.next();
            Object object2 = (ConnectionResult)this.zzflw.get(zzh2);
            if (((ConnectionResult)object2).isSuccess()) {
                bl = false;
            }
            zzh2 = zzh2.zzagy();
            object2 = String.valueOf(object2);
            arrayList.add(new StringBuilder(String.valueOf(zzh2).length() + 2 + String.valueOf(object2).length()).append((String)((Object)zzh2)).append(": ").append((String)object2).toString());
        }
        object = new StringBuilder();
        if (bl) {
            ((StringBuilder)object).append("None of the queried APIs are available. ");
        } else {
            ((StringBuilder)object).append("Some of the queried APIs are unavailable. ");
        }
        ((StringBuilder)object).append(TextUtils.join((CharSequence)"; ", arrayList));
        return ((StringBuilder)object).toString();
    }

    public final ArrayMap<zzh<?>, ConnectionResult> zzagj() {
        return this.zzflw;
    }
}

