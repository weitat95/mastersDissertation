/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzd;
import com.google.android.gms.internal.zzaru;
import com.google.android.gms.internal.zzasl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HitBuilders {

    public static class ExceptionBuilder
    extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            this.set("&t", "exception");
        }

        public ExceptionBuilder setDescription(String string2) {
            this.set("&exd", string2);
            return this;
        }

        public ExceptionBuilder setFatal(boolean bl) {
            this.set("&exf", zzasl.zzak(bl));
            return this;
        }
    }

    public static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> zzdpb = new HashMap<String, String>();
        private ProductAction zzdpc;
        private Map<String, List<Product>> zzdpd = new HashMap<String, List<Product>>();
        private List<Promotion> zzdpe = new ArrayList<Promotion>();
        private List<Product> zzdpf = new ArrayList<Product>();

        protected HitBuilder() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Map<String, String> build() {
            HashMap<String, String> hashMap = new HashMap<String, String>(this.zzdpb);
            if (this.zzdpc != null) {
                hashMap.putAll(this.zzdpc.build());
            }
            Object object = this.zzdpe.iterator();
            int n = 1;
            while (object.hasNext()) {
                hashMap.putAll(object.next().zzdr(zzd.zzao(n)));
                ++n;
            }
            object = this.zzdpf.iterator();
            n = 1;
            while (object.hasNext()) {
                hashMap.putAll(((Product)object.next()).zzdr(zzd.zzam(n)));
                ++n;
            }
            Iterator<Map.Entry<String, List<Product>>> iterator = this.zzdpd.entrySet().iterator();
            n = 1;
            while (iterator.hasNext()) {
                Map.Entry<String, List<Product>> entry = iterator.next();
                object = entry.getValue();
                String string2 = zzd.zzar(n);
                Iterator iterator2 = object.iterator();
                int n2 = 1;
                while (iterator2.hasNext()) {
                    Product product = (Product)iterator2.next();
                    object = String.valueOf(string2);
                    String string3 = String.valueOf(zzd.zzaq(n2));
                    object = string3.length() != 0 ? ((String)object).concat(string3) : new String((String)object);
                    hashMap.putAll(product.zzdr((String)object));
                    ++n2;
                }
                if (!TextUtils.isEmpty((CharSequence)entry.getKey())) {
                    object = String.valueOf(string2);
                    string2 = String.valueOf("nm");
                    object = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
                    hashMap.put((String)object, entry.getKey());
                }
                ++n;
            }
            return hashMap;
        }

        public final T set(String string2, String string3) {
            if (string2 != null) {
                this.zzdpb.put(string2, string3);
                return (T)this;
            }
            zzaru.zzcu("HitBuilder.set() called with a null paramName.");
            return (T)this;
        }
    }

    public static class ScreenViewBuilder
    extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            this.set("&t", "screenview");
        }
    }

}

