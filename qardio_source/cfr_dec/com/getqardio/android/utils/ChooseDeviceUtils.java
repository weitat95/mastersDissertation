/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.getqardio.android.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.getqardio.android.ui.activity.MainActivity;

public class ChooseDeviceUtils {
    public static Intent getMainIntent(Context context, long l) {
        boolean bl = ChooseDeviceUtils.isQardioArmKnown(context, l);
        boolean bl2 = ChooseDeviceUtils.isQardioBaseKnown(context, l);
        if (bl) {
            return MainActivity.getStartIntent(context);
        }
        if (bl2) {
            return MainActivity.getStartIntent(context, 2131821477);
        }
        return MainActivity.getStartIntent(context, true);
    }

    public static boolean isQardioArmKnown(Context context, long l) {
        return (context.getSharedPreferences("choose_device_sp_storage", 0).getInt(Long.toString(l), 0) & 1) == 1;
    }

    public static boolean isQardioBaseKnown(Context context, long l) {
        boolean bl = false;
        if ((context.getSharedPreferences("choose_device_sp_storage", 0).getInt(Long.toString(l), 0) & 2) == 2) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void setQardioArmKnown(Context context, long l, boolean bl) {
        context = context.getSharedPreferences("choose_device_sp_storage", 0);
        int n = context.getInt(Long.toString(l), 0);
        n = bl ? (n |= 1) : (n &= 0xFFFFFFFE);
        context = context.edit();
        context.putInt(Long.toString(l), n);
        context.commit();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void setQardioBaseKnown(Context context, long l, boolean bl) {
        context = context.getSharedPreferences("choose_device_sp_storage", 0);
        int n = context.getInt(Long.toString(l), 0);
        n = bl ? (n |= 2) : (n &= 0xFFFFFFFD);
        context = context.edit();
        context.putInt(Long.toString(l), n);
        context.commit();
    }
}

