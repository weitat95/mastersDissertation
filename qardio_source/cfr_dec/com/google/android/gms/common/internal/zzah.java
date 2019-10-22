/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Intent;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import java.util.Arrays;

public final class zzah {
    private final ComponentName mComponentName;
    private final String zzdrp;
    private final String zzgak;
    private final int zzgal;

    public zzah(String string2, String string3, int n) {
        this.zzdrp = zzbq.zzgm(string2);
        this.zzgak = zzbq.zzgm(string3);
        this.mComponentName = null;
        this.zzgal = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof zzah)) {
                    return false;
                }
                object = (zzah)object;
                if (!zzbg.equal(this.zzdrp, ((zzah)object).zzdrp) || !zzbg.equal(this.zzgak, ((zzah)object).zzgak) || !zzbg.equal((Object)this.mComponentName, (Object)((zzah)object).mComponentName) || this.zzgal != ((zzah)object).zzgal) break block5;
            }
            return true;
        }
        return false;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final String getPackage() {
        return this.zzgak;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzdrp, this.zzgak, this.mComponentName, this.zzgal});
    }

    public final String toString() {
        if (this.zzdrp == null) {
            return this.mComponentName.flattenToString();
        }
        return this.zzdrp;
    }

    public final int zzalk() {
        return this.zzgal;
    }

    public final Intent zzall() {
        if (this.zzdrp != null) {
            return new Intent(this.zzdrp).setPackage(this.zzgak);
        }
        return new Intent().setComponent(this.mComponentName);
    }
}

