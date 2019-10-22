/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import java.util.Arrays;

final class zzfju {
    final int tag;
    final byte[] zzjng;

    zzfju(int n, byte[] arrby) {
        this.tag = n;
        this.zzjng = arrby;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof zzfju)) {
                    return false;
                }
                object = (zzfju)object;
                if (this.tag != ((zzfju)object).tag || !Arrays.equals(this.zzjng, ((zzfju)object).zzjng)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (this.tag + 527) * 31 + Arrays.hashCode(this.zzjng);
    }
}

