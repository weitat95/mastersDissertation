/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.firebase.messaging;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.firebase.messaging.zza;
import com.google.firebase.messaging.zze;
import com.google.firebase.messaging.zzf;
import java.util.Map;
import java.util.Set;

public final class RemoteMessage
extends zzbfm {
    public static final Parcelable.Creator<RemoteMessage> CREATOR = new zzf();
    Bundle mBundle;
    private Map<String, String> zzdpq;
    private Notification zzoah;

    RemoteMessage(Bundle bundle) {
        this.mBundle = bundle;
    }

    public final Map<String, String> getData() {
        if (this.zzdpq == null) {
            this.zzdpq = new ArrayMap<String, String>();
            for (String string2 : this.mBundle.keySet()) {
                Object object = this.mBundle.get(string2);
                if (!(object instanceof String)) continue;
                object = (String)object;
                if (string2.startsWith("google.") || string2.startsWith("gcm.") || string2.equals("from") || string2.equals("message_type") || string2.equals("collapse_key")) continue;
                this.zzdpq.put(string2, (String)object);
            }
        }
        return this.zzdpq;
    }

    public final String getFrom() {
        return this.mBundle.getString("from");
    }

    public final Notification getNotification() {
        if (this.zzoah == null && zza.zzag(this.mBundle)) {
            this.zzoah = new Notification(this.mBundle, null);
        }
        return this.zzoah;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.mBundle, false);
        zzbfp.zzai(parcel, n);
    }

    public static class Notification {
        private final String mTag;
        private final String zzbtu;
        private final String zzemt;
        private final String zzoai;
        private final String[] zzoaj;
        private final String zzoak;
        private final String[] zzoal;
        private final String zzoam;
        private final String zzoan;
        private final String zzoao;
        private final String zzoap;
        private final Uri zzoaq;

        private Notification(Bundle bundle) {
            this.zzemt = zza.zze(bundle, "gcm.n.title");
            this.zzoai = zza.zzh(bundle, "gcm.n.title");
            this.zzoaj = Notification.zzk(bundle, "gcm.n.title");
            this.zzbtu = zza.zze(bundle, "gcm.n.body");
            this.zzoak = zza.zzh(bundle, "gcm.n.body");
            this.zzoal = Notification.zzk(bundle, "gcm.n.body");
            this.zzoam = zza.zze(bundle, "gcm.n.icon");
            this.zzoan = zza.zzai(bundle);
            this.mTag = zza.zze(bundle, "gcm.n.tag");
            this.zzoao = zza.zze(bundle, "gcm.n.color");
            this.zzoap = zza.zze(bundle, "gcm.n.click_action");
            this.zzoaq = zza.zzah(bundle);
        }

        /* synthetic */ Notification(Bundle bundle, zze zze2) {
            this(bundle);
        }

        private static String[] zzk(Bundle arrobject, String arrstring) {
            if ((arrobject = zza.zzi((Bundle)arrobject, (String)arrstring)) == null) {
                return null;
            }
            arrstring = new String[arrobject.length];
            for (int i = 0; i < arrobject.length; ++i) {
                arrstring[i] = String.valueOf(arrobject[i]);
            }
            return arrstring;
        }

        public String getBody() {
            return this.zzbtu;
        }
    }

}

