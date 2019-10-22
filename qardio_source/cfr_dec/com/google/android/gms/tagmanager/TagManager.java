/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentCallbacks
 *  android.content.Context
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.net.Uri;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzal;
import com.google.android.gms.tagmanager.zzat;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzei;
import com.google.android.gms.tagmanager.zzfn;
import com.google.android.gms.tagmanager.zzfo;
import com.google.android.gms.tagmanager.zzg;
import com.google.android.gms.tagmanager.zzgb;
import com.google.android.gms.tagmanager.zzgc;
import com.google.android.gms.tagmanager.zzgd;
import com.google.android.gms.tagmanager.zzge;
import com.google.android.gms.tagmanager.zzv;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager zzkkd;
    private final Context mContext;
    private final DataLayer zzkde;
    private final zzal zzkid;
    private final zza zzkka;
    private final zzfn zzkkb;
    private final ConcurrentMap<String, zzv> zzkkc;

    private TagManager(Context context, zza zza2, DataLayer dataLayer, zzfn zzfn2) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.zzkkb = zzfn2;
        this.zzkka = zza2;
        this.zzkkc = new ConcurrentHashMap<String, zzv>();
        this.zzkde = dataLayer;
        this.zzkde.zza(new zzgb(this));
        this.zzkde.zza(new zzg(this.mContext));
        this.zzkid = new zzal();
        this.mContext.registerComponentCallbacks((ComponentCallbacks)new zzgd(this));
        com.google.android.gms.tagmanager.zza.zzeb(this.mContext);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TagManager getInstance(Context object) {
        synchronized (TagManager.class) {
            if (zzkkd != null) return zzkkd;
            if (object == null) {
                zzdj.e("TagManager.getInstance requires non-null context.");
                throw new NullPointerException();
            }
            zzkkd = new TagManager((Context)object, new zzgc(), new DataLayer(new zzat((Context)object)), zzfo.zzbgg());
            return zzkkd;
        }
    }

    static /* synthetic */ void zza(TagManager tagManager, String string2) {
        tagManager.zzly(string2);
    }

    private final void zzly(String string2) {
        Iterator iterator = this.zzkkc.values().iterator();
        while (iterator.hasNext()) {
            ((zzv)iterator.next()).zzld(string2);
        }
    }

    public void dispatch() {
        this.zzkkb.dispatch();
    }

    public final boolean zzb(zzv zzv2) {
        return this.zzkkc.remove(zzv2.getContainerId()) != null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final boolean zzq(Uri object) {
        synchronized (this) {
            Iterator iterator;
            zzei zzei2 = zzei.zzbfo();
            if (!zzei2.zzq((Uri)object)) return false;
            object = zzei2.getContainerId();
            int n = zzge.zzkkf[zzei2.zzbfp().ordinal()];
            switch (n) {
                case 1: {
                    if ((object = (zzv)this.zzkkc.get(object)) == null) return true;
                    ((zzv)object).zzle(null);
                    ((zzv)object).refresh();
                }
                default: {
                    return true;
                }
                case 2: 
                case 3: {
                    iterator = this.zzkkc.keySet().iterator();
                    break;
                }
            }
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                zzv zzv2 = (zzv)this.zzkkc.get(string2);
                if (string2.equals(object)) {
                    zzv2.zzle(zzei2.zzbfq());
                    zzv2.refresh();
                    continue;
                }
                if (zzv2.zzbdv() == null) continue;
                zzv2.zzle(null);
                zzv2.refresh();
            }
            return true;
        }
    }

    public static interface zza {
    }

}

