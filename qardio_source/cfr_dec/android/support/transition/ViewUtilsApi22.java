/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.util.Log
 *  android.view.View
 */
package android.support.transition;

import android.annotation.SuppressLint;
import android.support.transition.ViewUtilsApi21;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsApi22
extends ViewUtilsApi21 {
    private static Method sSetLeftTopRightBottomMethod;
    private static boolean sSetLeftTopRightBottomMethodFetched;

    ViewUtilsApi22() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"PrivateApi"})
    private void fetchSetLeftTopRightBottomMethod() {
        if (!sSetLeftTopRightBottomMethodFetched) {
            try {
                sSetLeftTopRightBottomMethod = View.class.getDeclaredMethod("setLeftTopRightBottom", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                sSetLeftTopRightBottomMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)"ViewUtilsApi22", (String)"Failed to retrieve setLeftTopRightBottom method", (Throwable)noSuchMethodException);
            }
            sSetLeftTopRightBottomMethodFetched = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setLeftTopRightBottom(View view, int n, int n2, int n3, int n4) {
        this.fetchSetLeftTopRightBottomMethod();
        if (sSetLeftTopRightBottomMethod == null) return;
        try {
            sSetLeftTopRightBottomMethod.invoke((Object)view, n, n2, n3, n4);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }
}

