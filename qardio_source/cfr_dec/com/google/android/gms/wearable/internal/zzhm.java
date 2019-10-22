/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.internal.zzfe;

final class zzhm
implements zzcl<MessageApi.MessageListener> {
    private /* synthetic */ zzfe zzlhp;

    zzhm(zzfe zzfe2) {
        this.zzlhp = zzfe2;
    }

    @Override
    public final void zzahz() {
    }

    @Override
    public final /* synthetic */ void zzu(Object object) {
        ((MessageApi.MessageListener)object).onMessageReceived(this.zzlhp);
    }
}

