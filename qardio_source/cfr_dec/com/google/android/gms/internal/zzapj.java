/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzh;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzapj
extends zzh<zzapj> {
    private ProductAction zzdpc;
    private final Map<String, List<Product>> zzdpd;
    private final List<Promotion> zzdpe;
    private final List<Product> zzdpf = new ArrayList<Product>();

    public zzapj() {
        this.zzdpe = new ArrayList<Promotion>();
        this.zzdpd = new HashMap<String, List<Product>>();
    }

    public final String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (!this.zzdpf.isEmpty()) {
            hashMap.put("products", this.zzdpf);
        }
        if (!this.zzdpe.isEmpty()) {
            hashMap.put("promotions", this.zzdpe);
        }
        if (!this.zzdpd.isEmpty()) {
            hashMap.put("impressions", this.zzdpd);
        }
        hashMap.put("productAction", this.zzdpc);
        return zzapj.zzl(hashMap);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ void zzb(zzh object) {
        zzapj zzapj2 = (zzapj)object;
        zzapj2.zzdpf.addAll(this.zzdpf);
        zzapj2.zzdpe.addAll(this.zzdpe);
        Iterator<Map.Entry<String, List<Product>>> iterator = this.zzdpd.entrySet().iterator();
        block0: do {
            if (!iterator.hasNext()) {
                if (this.zzdpc != null) {
                    zzapj2.zzdpc = this.zzdpc;
                }
                return;
            }
            Map.Entry<String, List<Product>> entry = iterator.next();
            String string2 = entry.getKey();
            Iterator<Product> iterator2 = entry.getValue().iterator();
            do {
                void var1_6;
                if (!iterator2.hasNext()) continue block0;
                Product product = iterator2.next();
                if (product == null) continue;
                if (string2 == null) {
                    String string3 = "";
                } else {
                    String string4 = string2;
                }
                if (!zzapj2.zzdpd.containsKey(var1_6)) {
                    zzapj2.zzdpd.put((String)var1_6, new ArrayList());
                }
                zzapj2.zzdpd.get(var1_6).add(product);
            } while (true);
            break;
        } while (true);
    }

    public final ProductAction zzvu() {
        return this.zzdpc;
    }

    public final List<Product> zzvv() {
        return Collections.unmodifiableList(this.zzdpf);
    }

    public final Map<String, List<Product>> zzvw() {
        return this.zzdpd;
    }

    public final List<Promotion> zzvx() {
        return Collections.unmodifiableList(this.zzdpe);
    }
}

