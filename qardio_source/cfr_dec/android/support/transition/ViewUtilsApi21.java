/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.util.Log
 *  android.view.View
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.transition.ViewUtilsApi19;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsApi21
extends ViewUtilsApi19 {
    private static Method sSetAnimationMatrixMethod;
    private static boolean sSetAnimationMatrixMethodFetched;
    private static Method sTransformMatrixToGlobalMethod;
    private static boolean sTransformMatrixToGlobalMethodFetched;
    private static Method sTransformMatrixToLocalMethod;
    private static boolean sTransformMatrixToLocalMethodFetched;

    ViewUtilsApi21() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void fetchSetAnimationMatrix() {
        if (!sSetAnimationMatrixMethodFetched) {
            try {
                sSetAnimationMatrixMethod = View.class.getDeclaredMethod("setAnimationMatrix", Matrix.class);
                sSetAnimationMatrixMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)"ViewUtilsApi21", (String)"Failed to retrieve setAnimationMatrix method", (Throwable)noSuchMethodException);
            }
            sSetAnimationMatrixMethodFetched = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void fetchTransformMatrixToGlobalMethod() {
        if (!sTransformMatrixToGlobalMethodFetched) {
            try {
                sTransformMatrixToGlobalMethod = View.class.getDeclaredMethod("transformMatrixToGlobal", Matrix.class);
                sTransformMatrixToGlobalMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)"ViewUtilsApi21", (String)"Failed to retrieve transformMatrixToGlobal method", (Throwable)noSuchMethodException);
            }
            sTransformMatrixToGlobalMethodFetched = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void fetchTransformMatrixToLocalMethod() {
        if (!sTransformMatrixToLocalMethodFetched) {
            try {
                sTransformMatrixToLocalMethod = View.class.getDeclaredMethod("transformMatrixToLocal", Matrix.class);
                sTransformMatrixToLocalMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)"ViewUtilsApi21", (String)"Failed to retrieve transformMatrixToLocal method", (Throwable)noSuchMethodException);
            }
            sTransformMatrixToLocalMethodFetched = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setAnimationMatrix(View view, Matrix matrix) {
        this.fetchSetAnimationMatrix();
        if (sSetAnimationMatrixMethod == null) return;
        try {
            sSetAnimationMatrixMethod.invoke((Object)view, new Object[]{matrix});
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException.getCause());
        }
        catch (InvocationTargetException invocationTargetException) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void transformMatrixToGlobal(View view, Matrix matrix) {
        this.fetchTransformMatrixToGlobalMethod();
        if (sTransformMatrixToGlobalMethod == null) return;
        try {
            sTransformMatrixToGlobalMethod.invoke((Object)view, new Object[]{matrix});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void transformMatrixToLocal(View view, Matrix matrix) {
        this.fetchTransformMatrixToLocalMethod();
        if (sTransformMatrixToLocalMethod == null) return;
        try {
            sTransformMatrixToLocalMethod.invoke((Object)view, new Object[]{matrix});
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

