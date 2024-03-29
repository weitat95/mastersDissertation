/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.app;

import android.app.Service;
import android.os.Build;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ServiceCompat {
    public static final int START_STICKY = 1;
    public static final int STOP_FOREGROUND_DETACH = 2;
    public static final int STOP_FOREGROUND_REMOVE = 1;

    private ServiceCompat() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void stopForeground(Service service, int n) {
        if (Build.VERSION.SDK_INT >= 24) {
            service.stopForeground(n);
            return;
        }
        boolean bl = (n & 1) != 0;
        service.stopForeground(bl);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StopForegroundFlags {
    }

}

