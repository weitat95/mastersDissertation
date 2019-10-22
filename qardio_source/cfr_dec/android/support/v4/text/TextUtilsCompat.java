/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package android.support.v4.text;

import android.os.Build;
import android.support.v4.text.ICUCompat;
import android.text.TextUtils;
import java.util.Locale;

public final class TextUtilsCompat {
    @Deprecated
    public static final Locale ROOT = new Locale("", "");

    private static int getLayoutDirectionFromFirstChar(Locale locale) {
        switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
            default: {
                return 0;
            }
            case 1: 
            case 2: 
        }
        return 1;
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale((Locale)locale);
        }
        if (locale != null && !locale.equals(ROOT)) {
            String string2 = ICUCompat.maximizeAndGetScript(locale);
            if (string2 == null) {
                return TextUtilsCompat.getLayoutDirectionFromFirstChar(locale);
            }
            if (string2.equalsIgnoreCase("Arab") || string2.equalsIgnoreCase("Hebr")) {
                return 1;
            }
        }
        return 0;
    }
}

