/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.DrawableContainer$DrawableContainerState
 *  android.util.Log
 */
package android.support.design.widget;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.Log;
import java.lang.reflect.Method;

class DrawableUtils {
    private static Method sSetConstantStateMethod;
    private static boolean sSetConstantStateMethodFetched;

    static boolean setContainerConstantState(DrawableContainer drawableContainer, Drawable.ConstantState constantState) {
        return DrawableUtils.setContainerConstantStateV9(drawableContainer, constantState);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean setContainerConstantStateV9(DrawableContainer drawableContainer, Drawable.ConstantState constantState) {
        if (!sSetConstantStateMethodFetched) {
            try {
                sSetConstantStateMethod = DrawableContainer.class.getDeclaredMethod("setConstantState", DrawableContainer.DrawableContainerState.class);
                sSetConstantStateMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)"DrawableUtils", (String)"Could not fetch setConstantState(). Oh well.");
            }
            sSetConstantStateMethodFetched = true;
        }
        if (sSetConstantStateMethod != null) {
            try {
                sSetConstantStateMethod.invoke((Object)drawableContainer, new Object[]{constantState});
                return true;
            }
            catch (Exception exception) {
                Log.e((String)"DrawableUtils", (String)"Could not invoke setConstantState(). Oh well.");
            }
        }
        return false;
    }
}

