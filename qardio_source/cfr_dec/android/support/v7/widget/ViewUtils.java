/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 */
package android.support.v7.widget;

import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
    private static Method sComputeFitSystemWindowsMethod;

    static {
        block3: {
            if (Build.VERSION.SDK_INT < 18) break block3;
            try {
                sComputeFitSystemWindowsMethod = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
                if (!sComputeFitSystemWindowsMethod.isAccessible()) {
                    sComputeFitSystemWindowsMethod.setAccessible(true);
                }
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.d((String)"ViewUtils", (String)"Could not find method computeFitSystemWindows. Oh well.");
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void computeFitSystemWindows(View view, Rect rect, Rect rect2) {
        if (sComputeFitSystemWindowsMethod == null) return;
        try {
            sComputeFitSystemWindowsMethod.invoke((Object)view, new Object[]{rect, rect2});
            return;
        }
        catch (Exception exception) {
            Log.d((String)"ViewUtils", (String)"Could not invoke computeFitSystemWindows", (Throwable)exception);
            return;
        }
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void makeOptionalFitsSystemWindows(View view) {
        if (Build.VERSION.SDK_INT < 16) return;
        try {
            Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke((Object)view, new Object[0]);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.d((String)"ViewUtils", (String)"Could not find method makeOptionalFitsSystemWindows. Oh well...");
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.d((String)"ViewUtils", (String)"Could not invoke makeOptionalFitsSystemWindows", (Throwable)invocationTargetException);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.d((String)"ViewUtils", (String)"Could not invoke makeOptionalFitsSystemWindows", (Throwable)illegalAccessException);
            return;
        }
    }
}

