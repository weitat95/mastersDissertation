/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Pair
 */
package com.mixpanel.android.util;

import android.os.Build;
import android.util.Pair;

public class MPPair<F, S>
extends Pair<F, S> {
    public MPPair(F f, S s) {
        super(f, s);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (Build.VERSION.SDK_INT > 16) {
            return super.equals(object);
        }
        boolean bl2 = bl;
        if (!(object instanceof Pair)) return bl2;
        object = (Pair)object;
        if (((Pair)object).first != this.first) {
            bl2 = bl;
            if (((Pair)object).first == null) return bl2;
            bl2 = bl;
            if (!((Pair)object).first.equals(this.first)) return bl2;
        }
        if (((Pair)object).second == this.second) return true;
        bl2 = bl;
        if (((Pair)object).second == null) return bl2;
        bl2 = bl;
        if (!((Pair)object).second.equals(this.second)) return bl2;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        if (Build.VERSION.SDK_INT > 16) {
            return super.hashCode();
        }
        int n2 = this.first == null ? 0 : this.first.hashCode();
        if (this.second == null) {
            return n2 ^ n;
        }
        n = this.second.hashCode();
        return n2 ^ n;
    }
}

