/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package android.support.transition;

import android.os.IBinder;
import android.support.transition.WindowIdImpl;

class WindowIdApi14
implements WindowIdImpl {
    private final IBinder mToken;

    WindowIdApi14(IBinder iBinder) {
        this.mToken = iBinder;
    }

    public boolean equals(Object object) {
        return object instanceof WindowIdApi14 && ((WindowIdApi14)object).mToken.equals((Object)this.mToken);
    }

    public int hashCode() {
        return this.mToken.hashCode();
    }
}

