/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.WindowId
 */
package android.support.transition;

import android.support.transition.WindowIdImpl;
import android.view.View;
import android.view.WindowId;

class WindowIdApi18
implements WindowIdImpl {
    private final WindowId mWindowId;

    WindowIdApi18(View view) {
        this.mWindowId = view.getWindowId();
    }

    public boolean equals(Object object) {
        return object instanceof WindowIdApi18 && ((WindowIdApi18)object).mWindowId.equals((Object)this.mWindowId);
    }

    public int hashCode() {
        return this.mWindowId.hashCode();
    }
}

