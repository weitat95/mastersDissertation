/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.google.firebase;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzca;
import com.google.android.gms.common.util.zzu;
import java.util.Arrays;

public final class FirebaseOptions {
    private final String zzenh;
    private final String zzmbb;
    private final String zzmbc;
    private final String zzmbd;
    private final String zzmbe;
    private final String zzmbf;
    private final String zzmbg;

    /*
     * Enabled aggressive block sorting
     */
    private FirebaseOptions(String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        boolean bl = !zzu.zzgs(string2);
        zzbq.zza(bl, "ApplicationId must be set.");
        this.zzenh = string2;
        this.zzmbb = string3;
        this.zzmbc = string4;
        this.zzmbd = string5;
        this.zzmbe = string6;
        this.zzmbf = string7;
        this.zzmbg = string8;
    }

    public static FirebaseOptions fromResource(Context object) {
        String string2 = ((zzca)(object = new zzca((Context)object))).getString("google_app_id");
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        return new FirebaseOptions(string2, ((zzca)object).getString("google_api_key"), ((zzca)object).getString("firebase_database_url"), ((zzca)object).getString("ga_trackingId"), ((zzca)object).getString("gcm_defaultSenderId"), ((zzca)object).getString("google_storage_bucket"), ((zzca)object).getString("project_id"));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof FirebaseOptions)) break block2;
                object = (FirebaseOptions)object;
                if (zzbg.equal(this.zzenh, ((FirebaseOptions)object).zzenh) && zzbg.equal(this.zzmbb, ((FirebaseOptions)object).zzmbb) && zzbg.equal(this.zzmbc, ((FirebaseOptions)object).zzmbc) && zzbg.equal(this.zzmbd, ((FirebaseOptions)object).zzmbd) && zzbg.equal(this.zzmbe, ((FirebaseOptions)object).zzmbe) && zzbg.equal(this.zzmbf, ((FirebaseOptions)object).zzmbf) && zzbg.equal(this.zzmbg, ((FirebaseOptions)object).zzmbg)) break block3;
            }
            return false;
        }
        return true;
    }

    public final String getApplicationId() {
        return this.zzenh;
    }

    public final String getGcmSenderId() {
        return this.zzmbe;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzenh, this.zzmbb, this.zzmbc, this.zzmbd, this.zzmbe, this.zzmbf, this.zzmbg});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("applicationId", this.zzenh).zzg("apiKey", this.zzmbb).zzg("databaseUrl", this.zzmbc).zzg("gcmSenderId", this.zzmbe).zzg("storageBucket", this.zzmbf).zzg("projectId", this.zzmbg).toString();
    }
}

