/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package android.support.v4.view;

import android.view.MotionEvent;

public final class MotionEventCompat {
    @Deprecated
    public static int getActionMasked(MotionEvent motionEvent) {
        return motionEvent.getActionMasked();
    }

    public static boolean isFromSource(MotionEvent motionEvent, int n) {
        return (motionEvent.getSource() & n) == n;
    }
}

