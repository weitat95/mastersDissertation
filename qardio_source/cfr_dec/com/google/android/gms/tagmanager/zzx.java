/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzv;

final class zzx
extends Handler {
    private final ContainerHolder.ContainerAvailableListener zzkdq;
    private /* synthetic */ zzv zzkdr;

    public final void handleMessage(Message object) {
        switch (object.what) {
            default: {
                zzdj.e("Don't know how to handle this message.");
                return;
            }
            case 1: 
        }
        object = (String)object.obj;
        this.zzkdq.onContainerAvailable(this.zzkdr, (String)object);
    }
}

