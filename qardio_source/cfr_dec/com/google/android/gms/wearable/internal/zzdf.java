/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.zzdb;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzdf
extends zzc
implements DataItem {
    private final int zzhwi;

    public zzdf(DataHolder dataHolder, int n, int n2) {
        super(dataHolder, n);
        this.zzhwi = n2;
    }

    public final Map<String, DataItemAsset> getAssets() {
        HashMap<String, DataItemAsset> hashMap = new HashMap<String, DataItemAsset>(this.zzhwi);
        for (int i = 0; i < this.zzhwi; ++i) {
            zzdb zzdb2 = new zzdb(this.zzfqt, this.zzfvx + i);
            if (zzdb2.getDataItemKey() == null) continue;
            hashMap.put(zzdb2.getDataItemKey(), zzdb2);
        }
        return hashMap;
    }

    @Override
    public final byte[] getData() {
        return this.getByteArray("data");
    }

    @Override
    public final Uri getUri() {
        return Uri.parse((String)this.getString("path"));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final String toString() {
        void var3_4;
        boolean bl = Log.isLoggable((String)"DataItem", (int)3);
        byte[] arrby = this.getData();
        Object object = this.getAssets();
        StringBuilder stringBuilder = new StringBuilder("DataItemRef{ ");
        String string2 = String.valueOf((Object)this.getUri());
        stringBuilder.append(new StringBuilder(String.valueOf(string2).length() + 4).append("uri=").append(string2).toString());
        if (arrby == null) {
            String string3 = "null";
        } else {
            Integer n = arrby.length;
        }
        String string4 = String.valueOf(var3_4);
        stringBuilder.append(new StringBuilder(String.valueOf(string4).length() + 9).append(", dataSz=").append(string4).toString());
        int n = object.size();
        stringBuilder.append(new StringBuilder(23).append(", numAssets=").append(n).toString());
        if (bl && !object.isEmpty()) {
            stringBuilder.append(", assets=[");
            object = object.entrySet().iterator();
            String string5 = "";
            while (object.hasNext()) {
                void var3_7;
                Object object2 = (Map.Entry)object.next();
                string2 = (String)object2.getKey();
                object2 = ((DataItemAsset)object2.getValue()).getId();
                stringBuilder.append(new StringBuilder(String.valueOf(var3_7).length() + 2 + String.valueOf(string2).length() + String.valueOf(object2).length()).append((String)var3_7).append(string2).append(": ").append((String)object2).toString());
                String string6 = ", ";
            }
            stringBuilder.append("]");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}

