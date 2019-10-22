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
package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.zzb;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public final class ConnectionResult
extends zzbfm {
    public static final Parcelable.Creator<ConnectionResult> CREATOR;
    public static final ConnectionResult zzfkr;
    private final int zzcd;
    private int zzeck;
    private final PendingIntent zzeeo;
    private final String zzfks;

    static {
        zzfkr = new ConnectionResult(0);
        CREATOR = new zzb();
    }

    public ConnectionResult(int n) {
        this(n, null, null);
    }

    ConnectionResult(int n, int n2, PendingIntent pendingIntent, String string2) {
        this.zzeck = n;
        this.zzcd = n2;
        this.zzeeo = pendingIntent;
        this.zzfks = string2;
    }

    public ConnectionResult(int n, PendingIntent pendingIntent) {
        this(n, pendingIntent, null);
    }

    public ConnectionResult(int n, PendingIntent pendingIntent, String string2) {
        this(1, n, pendingIntent, string2);
    }

    static String getStatusString(int n) {
        switch (n) {
            default: {
                return new StringBuilder(31).append("UNKNOWN_ERROR_CODE(").append(n).append(")").toString();
            }
            case 0: {
                return "SUCCESS";
            }
            case 1: {
                return "SERVICE_MISSING";
            }
            case 2: {
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            }
            case 3: {
                return "SERVICE_DISABLED";
            }
            case 4: {
                return "SIGN_IN_REQUIRED";
            }
            case 5: {
                return "INVALID_ACCOUNT";
            }
            case 6: {
                return "RESOLUTION_REQUIRED";
            }
            case 7: {
                return "NETWORK_ERROR";
            }
            case 8: {
                return "INTERNAL_ERROR";
            }
            case 9: {
                return "SERVICE_INVALID";
            }
            case 10: {
                return "DEVELOPER_ERROR";
            }
            case 11: {
                return "LICENSE_CHECK_FAILED";
            }
            case 13: {
                return "CANCELED";
            }
            case 14: {
                return "TIMEOUT";
            }
            case 15: {
                return "INTERRUPTED";
            }
            case 16: {
                return "API_UNAVAILABLE";
            }
            case 17: {
                return "SIGN_IN_FAILED";
            }
            case 18: {
                return "SERVICE_UPDATING";
            }
            case 19: {
                return "SERVICE_MISSING_PERMISSION";
            }
            case 20: {
                return "RESTRICTED_PROFILE";
            }
            case 21: {
                return "API_VERSION_UPDATE_REQUIRED";
            }
            case 1500: {
                return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
            }
            case 99: {
                return "UNFINISHED";
            }
            case -1: 
        }
        return "UNKNOWN";
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof ConnectionResult)) {
                    return false;
                }
                object = (ConnectionResult)object;
                if (this.zzcd != ((ConnectionResult)object).zzcd || !zzbg.equal((Object)this.zzeeo, (Object)((ConnectionResult)object).zzeeo) || !zzbg.equal(this.zzfks, ((ConnectionResult)object).zzfks)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int getErrorCode() {
        return this.zzcd;
    }

    public final String getErrorMessage() {
        return this.zzfks;
    }

    public final PendingIntent getResolution() {
        return this.zzeeo;
    }

    public final boolean hasResolution() {
        return this.zzcd != 0 && this.zzeeo != null;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzcd, this.zzeeo, this.zzfks});
    }

    public final boolean isSuccess() {
        return this.zzcd == 0;
    }

    public final void startResolutionForResult(Activity activity, int n) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(this.zzeeo.getIntentSender(), n, null, 0, 0, 0);
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("statusCode", ConnectionResult.getStatusString(this.zzcd)).zzg("resolution", (Object)this.zzeeo).zzg("message", this.zzfks).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zzc(parcel, 2, this.getErrorCode());
        zzbfp.zza(parcel, 3, (Parcelable)this.getResolution(), n, false);
        zzbfp.zza(parcel, 4, this.getErrorMessage(), false);
        zzbfp.zzai(parcel, n2);
    }
}

