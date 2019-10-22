/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzg;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public final class Status
extends zzbfm
implements Result,
ReflectedParcelable {
    public static final Parcelable.Creator<Status> CREATOR;
    public static final Status zzfni;
    public static final Status zzfnj;
    public static final Status zzfnk;
    public static final Status zzfnl;
    public static final Status zzfnm;
    public static final Status zzfnn;
    private static Status zzfno;
    private final int zzcd;
    private int zzeck;
    private final PendingIntent zzeeo;
    private final String zzfks;

    static {
        zzfni = new Status(0);
        zzfnj = new Status(14);
        zzfnk = new Status(8);
        zzfnl = new Status(15);
        zzfnm = new Status(16);
        zzfnn = new Status(17);
        zzfno = new Status(18);
        CREATOR = new zzg();
    }

    public Status(int n) {
        this(n, null);
    }

    Status(int n, int n2, String string2, PendingIntent pendingIntent) {
        this.zzeck = n;
        this.zzcd = n2;
        this.zzfks = string2;
        this.zzeeo = pendingIntent;
    }

    public Status(int n, String string2) {
        this(1, n, string2, null);
    }

    public Status(int n, String string2, PendingIntent pendingIntent) {
        this(1, n, string2, pendingIntent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof Status)) break block2;
                object = (Status)object;
                if (this.zzeck == ((Status)object).zzeck && this.zzcd == ((Status)object).zzcd && zzbg.equal(this.zzfks, ((Status)object).zzfks) && zzbg.equal((Object)this.zzeeo, (Object)((Status)object).zzeeo)) break block3;
            }
            return false;
        }
        return true;
    }

    @Override
    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzcd;
    }

    public final String getStatusMessage() {
        return this.zzfks;
    }

    public final boolean hasResolution() {
        return this.zzeeo != null;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzeck, this.zzcd, this.zzfks, this.zzeeo});
    }

    public final boolean isSuccess() {
        return this.zzcd <= 0;
    }

    public final void startResolutionForResult(Activity activity, int n) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(this.zzeeo.getIntentSender(), n, null, 0, 0, 0);
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("statusCode", this.zzagx()).zzg("resolution", (Object)this.zzeeo).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.getStatusCode());
        zzbfp.zza(parcel, 2, this.getStatusMessage(), false);
        zzbfp.zza(parcel, 3, (Parcelable)this.zzeeo, n, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }

    public final String zzagx() {
        if (this.zzfks != null) {
            return this.zzfks;
        }
        return CommonStatusCodes.getStatusCodeString(this.zzcd);
    }
}

