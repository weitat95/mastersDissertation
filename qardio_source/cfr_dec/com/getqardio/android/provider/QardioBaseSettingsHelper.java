/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.getqardio.android.provider;

import android.content.Context;
import android.content.SharedPreferences;
import com.getqardio.android.datamodel.QardioBaseSettings;

public class QardioBaseSettingsHelper {
    /*
     * Enabled aggressive block sorting
     */
    public static void setCurrentBaseSettings(Context context, QardioBaseSettings qardioBaseSettings) {
        context = context.getSharedPreferences("qb_switch_settings", 0).edit();
        if (qardioBaseSettings != null) {
            context.putBoolean("com.getqardio.android.HAPTIC_SETTING", qardioBaseSettings.enableHaptic);
            context.putBoolean("com.getqardio.android.COMPOSITION_SETTING", qardioBaseSettings.enableComposition);
        } else {
            context.putBoolean("com.getqardio.android.COMPOSITION_SETTING", false);
        }
        context.commit();
    }
}

