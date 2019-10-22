/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.support.transition.ViewGroupOverlayApi18;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtilsApi14;
import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtilsApi18
extends ViewGroupUtilsApi14 {
    private static Method sSuppressLayoutMethod;
    private static boolean sSuppressLayoutMethodFetched;

    ViewGroupUtilsApi18() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void fetchSuppressLayoutMethod() {
        if (!sSuppressLayoutMethodFetched) {
            try {
                sSuppressLayoutMethod = ViewGroup.class.getDeclaredMethod("suppressLayout", Boolean.TYPE);
                sSuppressLayoutMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)"ViewUtilsApi18", (String)"Failed to retrieve suppressLayout method", (Throwable)noSuchMethodException);
            }
            sSuppressLayoutMethodFetched = true;
        }
    }

    @Override
    public ViewGroupOverlayImpl getOverlay(ViewGroup viewGroup) {
        return new ViewGroupOverlayApi18(viewGroup);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void suppressLayout(ViewGroup viewGroup, boolean bl) {
        this.fetchSuppressLayoutMethod();
        if (sSuppressLayoutMethod == null) return;
        try {
            sSuppressLayoutMethod.invoke((Object)viewGroup, bl);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.i((String)"ViewUtilsApi18", (String)"Failed to invoke suppressLayout method", (Throwable)illegalAccessException);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.i((String)"ViewUtilsApi18", (String)"Error invoking suppressLayout method", (Throwable)invocationTargetException);
            return;
        }
    }
}

