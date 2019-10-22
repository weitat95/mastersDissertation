/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningTaskInfo
 *  android.content.ComponentName
 *  android.content.Context
 */
package com.getqardio.android.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import com.getqardio.android.ui.activity.BPMeasureActivity;
import com.getqardio.android.ui.activity.ImageSlideshowActivity;
import java.util.List;

public class ApplicationUtils {
    public static String getCurrentActivityName(Context object) {
        List<ActivityManager.RunningTaskInfo> list = ApplicationUtils.getRunningTasks(object);
        String string2 = "";
        object = string2;
        if (list != null) {
            object = string2;
            if (!list.isEmpty()) {
                object = list.get((int)0).topActivity.getClassName();
            }
        }
        return object;
    }

    private static List<ActivityManager.RunningTaskInfo> getRunningTasks(Context context) {
        return ((ActivityManager)context.getSystemService("activity")).getRunningTasks(1);
    }

    public static boolean isApplicationInForeground(Context context) {
        boolean bl = false;
        List<ActivityManager.RunningTaskInfo> list = ApplicationUtils.getRunningTasks(context);
        boolean bl2 = bl;
        if (!list.isEmpty()) {
            bl2 = bl;
            if (list.get((int)0).topActivity.getPackageName().equalsIgnoreCase(context.getPackageName())) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static boolean isBPMeasurementInProcess(Context object) {
        return ((String)(object = ApplicationUtils.getCurrentActivityName((Context)object))).equals(BPMeasureActivity.class.getName()) || ((String)object).equals(ImageSlideshowActivity.class.getName());
    }
}

