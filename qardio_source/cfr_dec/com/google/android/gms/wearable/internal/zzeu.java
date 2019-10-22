/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.internal.zzev;

public final class zzeu
implements MessageApi {
    @Override
    public final PendingResult<MessageApi.SendMessageResult> sendMessage(GoogleApiClient googleApiClient, String string2, String string3, byte[] arrby) {
        return googleApiClient.zzd(new zzev(this, googleApiClient, string2, string3, arrby));
    }
}

