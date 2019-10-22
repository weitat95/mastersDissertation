/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzcjo;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckf;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzcli;
import com.google.android.gms.internal.zzclj;
import com.google.android.gms.internal.zzclq;

@TargetApi(value=14)
final class zzckb
implements Application.ActivityLifecycleCallbacks {
    private /* synthetic */ zzcjn zzjhc;

    private zzckb(zzcjn zzcjn2) {
        this.zzjhc = zzcjn2;
    }

    /* synthetic */ zzckb(zzcjn zzcjn2, zzcjo zzcjo2) {
        this(zzcjn2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onActivityCreated(Activity object, Bundle bundle) {
        Object object2;
        try {
            Uri uri;
            ((zzcjk)this.zzjhc).zzawy().zzazj().log("onActivityCreated");
            object2 = object.getIntent();
            if (object2 != null && (uri = object2.getData()) != null && uri.isHierarchical()) {
                if (bundle == null) {
                    Bundle bundle2 = ((zzcjk)this.zzjhc).zzawu().zzp(uri);
                    ((zzcjk)this.zzjhc).zzawu();
                    object2 = zzclq.zzo((Intent)object2) ? "gs" : "auto";
                    if (bundle2 != null) {
                        this.zzjhc.zzc((String)object2, "_cmp", bundle2);
                    }
                }
                if (TextUtils.isEmpty((CharSequence)(object2 = uri.getQueryParameter("referrer")))) {
                    return;
                }
                boolean bl = ((String)object2).contains("gclid") && (((String)object2).contains("utm_campaign") || ((String)object2).contains("utm_source") || ((String)object2).contains("utm_medium") || ((String)object2).contains("utm_term") || ((String)object2).contains("utm_content"));
                if (!bl) {
                    ((zzcjk)this.zzjhc).zzawy().zzazi().log("Activity created with data 'referrer' param without gclid and at least one utm field");
                    return;
                }
                ((zzcjk)this.zzjhc).zzawy().zzazi().zzj("Activity created with referrer", object2);
                if (!TextUtils.isEmpty((CharSequence)object2)) {
                    this.zzjhc.zzb("auto", "_ldl", object2);
                }
            }
        }
        catch (Throwable throwable) {
            ((zzcjk)this.zzjhc).zzawy().zzazd().zzj("Throwable caught in onActivityCreated", throwable);
        }
        object2 = ((zzcjk)this.zzjhc).zzawq();
        if (bundle != null && (bundle = bundle.getBundle("com.google.firebase.analytics.screen_service")) != null) {
            object = ((zzckc)object2).zzq((Activity)object);
            object.zziwm = bundle.getLong("id");
            object.zziwk = bundle.getString("name");
            object.zziwl = bundle.getString("referrer_name");
            return;
        }
    }

    public final void onActivityDestroyed(Activity activity) {
        ((zzcjk)this.zzjhc).zzawq().onActivityDestroyed(activity);
    }

    public final void onActivityPaused(Activity object) {
        ((zzcjk)this.zzjhc).zzawq().onActivityPaused((Activity)object);
        object = ((zzcjk)this.zzjhc).zzaww();
        long l = ((zzcjk)object).zzws().elapsedRealtime();
        ((zzcjk)object).zzawx().zzg(new zzclj((zzclf)object, l));
    }

    public final void onActivityResumed(Activity object) {
        ((zzcjk)this.zzjhc).zzawq().onActivityResumed((Activity)object);
        object = ((zzcjk)this.zzjhc).zzaww();
        long l = ((zzcjk)object).zzws().elapsedRealtime();
        ((zzcjk)object).zzawx().zzg(new zzcli((zzclf)object, l));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        ((zzcjk)this.zzjhc).zzawq().onActivitySaveInstanceState(activity, bundle);
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }
}

