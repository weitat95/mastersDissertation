/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity.Util;

import java.util.Calendar;

public class ActivityUtils {
    public static boolean isSameDay(long l, long l2) {
        boolean bl = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        l = calendar.getTimeInMillis();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l2);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        if (l == calendar.getTimeInMillis()) {
            bl = true;
        }
        return bl;
    }
}

