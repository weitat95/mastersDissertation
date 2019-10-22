/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.zzbx;
import com.google.android.gms.wearable.internal.zzbz;
import com.google.android.gms.wearable.internal.zzcb;

public final class zzbw
implements DataApi {
    @Override
    public final PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(GoogleApiClient googleApiClient, Uri uri) {
        return this.deleteDataItems(googleApiClient, uri, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(GoogleApiClient googleApiClient, Uri uri, int n) {
        boolean bl = true;
        zzc.zzb((Object)uri, "uri must not be null");
        boolean bl2 = bl;
        if (n != 0) {
            bl2 = n == 1 ? bl : false;
        }
        zzbq.checkArgument(bl2, "invalid filter type");
        return googleApiClient.zzd(new zzcb(this, googleApiClient, uri, n));
    }

    @Override
    public final PendingResult<DataItemBuffer> getDataItems(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzbz(this, googleApiClient));
    }

    @Override
    public final PendingResult<DataApi.DataItemResult> putDataItem(GoogleApiClient googleApiClient, PutDataRequest putDataRequest) {
        return googleApiClient.zzd(new zzbx(this, googleApiClient, putDataRequest));
    }
}

