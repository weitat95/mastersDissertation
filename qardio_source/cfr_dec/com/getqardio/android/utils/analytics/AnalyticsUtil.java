/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.analytics;

import android.content.Context;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.utils.ui.Convert;
import java.util.Locale;

public class AnalyticsUtil {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getQardioBaseMode(Context context, QardioBaseDevice.BaseMode baseMode) {
        int n;
        switch (1.$SwitchMap$com$getqardio$android$baseble$QardioBaseDevice$BaseMode[baseMode.ordinal()]) {
            default: {
                n = 2131361989;
                do {
                    return Convert.getStringForLocale(context, n, Locale.ENGLISH);
                    break;
                } while (true);
            }
            case 1: {
                n = 2131361989;
                return Convert.getStringForLocale(context, n, Locale.ENGLISH);
            }
            case 2: {
                n = 2131361994;
                return Convert.getStringForLocale(context, n, Locale.ENGLISH);
            }
            case 3: {
                n = 2131361996;
                return Convert.getStringForLocale(context, n, Locale.ENGLISH);
            }
            case 4: 
        }
        n = 2131361991;
        return Convert.getStringForLocale(context, n, Locale.ENGLISH);
    }

}

