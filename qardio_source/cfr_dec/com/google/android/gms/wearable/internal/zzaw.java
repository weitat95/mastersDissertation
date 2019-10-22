/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.internal.zzax;
import com.google.android.gms.wearable.internal.zzay;

public final class zzaw
extends zzbfm {
    public static final Parcelable.Creator<zzaw> CREATOR = new zzax();
    private int type;
    private int zzljc;
    private int zzljd;
    private zzay zzlje;

    public zzaw(zzay zzay2, int n, int n2, int n3) {
        this.zzlje = zzay2;
        this.type = n;
        this.zzljc = n2;
        this.zzljd = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        String string2;
        String string3;
        String string4 = String.valueOf(this.zzlje);
        int n = this.type;
        switch (n) {
            default: {
                string3 = Integer.toString(n);
                break;
            }
            case 1: {
                string3 = "CHANNEL_OPENED";
                break;
            }
            case 2: {
                string3 = "CHANNEL_CLOSED";
                break;
            }
            case 4: {
                string3 = "OUTPUT_CLOSED";
                break;
            }
            case 3: {
                string3 = "INPUT_CLOSED";
            }
        }
        n = this.zzljc;
        switch (n) {
            default: {
                string2 = Integer.toString(n);
                break;
            }
            case 1: {
                string2 = "CLOSE_REASON_DISCONNECTED";
                break;
            }
            case 2: {
                string2 = "CLOSE_REASON_REMOTE_CLOSE";
                break;
            }
            case 3: {
                string2 = "CLOSE_REASON_LOCAL_CLOSE";
                break;
            }
            case 0: {
                string2 = "CLOSE_REASON_NORMAL";
            }
        }
        n = this.zzljd;
        return new StringBuilder(String.valueOf(string4).length() + 81 + String.valueOf(string3).length() + String.valueOf(string2).length()).append("ChannelEventParcelable[, channel=").append(string4).append(", type=").append(string3).append(", closeReason=").append(string2).append(", appErrorCode=").append(n).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlje, n, false);
        zzbfp.zzc(parcel, 3, this.type);
        zzbfp.zzc(parcel, 4, this.zzljc);
        zzbfp.zzc(parcel, 5, this.zzljd);
        zzbfp.zzai(parcel, n2);
    }

    public final void zza(ChannelApi.ChannelListener channelListener) {
        switch (this.type) {
            default: {
                int n = this.type;
                Log.w((String)"ChannelEventParcelable", (String)new StringBuilder(25).append("Unknown type: ").append(n).toString());
                return;
            }
            case 1: {
                channelListener.onChannelOpened(this.zzlje);
                return;
            }
            case 2: {
                channelListener.onChannelClosed(this.zzlje, this.zzljc, this.zzljd);
                return;
            }
            case 3: {
                channelListener.onInputClosed(this.zzlje, this.zzljc, this.zzljd);
                return;
            }
            case 4: 
        }
        channelListener.onOutputClosed(this.zzlje, this.zzljc, this.zzljd);
    }
}

