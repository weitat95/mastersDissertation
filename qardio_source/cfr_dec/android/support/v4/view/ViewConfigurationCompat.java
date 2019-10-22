/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.ViewConfiguration
 */
package android.support.v4.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import java.lang.reflect.Method;

@Deprecated
public final class ViewConfigurationCompat {
    private static Method sGetScaledScrollFactorMethod;

    static {
        block2: {
            if (Build.VERSION.SDK_INT != 25) break block2;
            try {
                sGetScaledScrollFactorMethod = ViewConfiguration.class.getDeclaredMethod("getScaledScrollFactor", new Class[0]);
            }
            catch (Exception exception) {
                Log.i((String)"ViewConfigCompat", (String)"Could not find method getScaledScrollFactor() on ViewConfiguration");
            }
        }
    }

    private static float getLegacyScrollFactor(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT >= 25 && sGetScaledScrollFactorMethod != null) {
            int n;
            try {
                n = (Integer)sGetScaledScrollFactorMethod.invoke((Object)viewConfiguration, new Object[0]);
            }
            catch (Exception exception) {
                Log.i((String)"ViewConfigCompat", (String)"Could not find method getScaledScrollFactor() on ViewConfiguration");
            }
            return n;
        }
        viewConfiguration = new TypedValue();
        if (context.getTheme().resolveAttribute(16842829, (TypedValue)viewConfiguration, true)) {
            return viewConfiguration.getDimension(context.getResources().getDisplayMetrics());
        }
        return 0.0f;
    }

    public static float getScaledHorizontalScrollFactor(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return viewConfiguration.getScaledHorizontalScrollFactor();
        }
        return ViewConfigurationCompat.getLegacyScrollFactor(viewConfiguration, context);
    }

    public static float getScaledVerticalScrollFactor(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return viewConfiguration.getScaledVerticalScrollFactor();
        }
        return ViewConfigurationCompat.getLegacyScrollFactor(viewConfiguration, context);
    }
}

