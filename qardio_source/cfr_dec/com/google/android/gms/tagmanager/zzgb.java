/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import java.util.Map;

final class zzgb
implements DataLayer.zzb {
    private /* synthetic */ TagManager zzkke;

    zzgb(TagManager tagManager) {
        this.zzkke = tagManager;
    }

    @Override
    public final void zzw(Map<String, Object> object) {
        if ((object = object.get("event")) != null) {
            TagManager.zza(this.zzkke, object.toString());
        }
    }
}

