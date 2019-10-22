/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.logging;

import com.facebook.common.logging.FLogDefaultLoggingDelegate;
import com.facebook.common.logging.LoggingDelegate;

public class FLog {
    private static LoggingDelegate sHandler = FLogDefaultLoggingDelegate.getInstance();

    public static void d(Class<?> class_, String string2, Object object) {
        if (sHandler.isLoggable(3)) {
            sHandler.d(FLog.getTag(class_), FLog.formatString(string2, object));
        }
    }

    public static void e(Class<?> class_, String string2) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(FLog.getTag(class_), string2);
        }
    }

    public static void e(Class<?> class_, String string2, Throwable throwable) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(FLog.getTag(class_), string2, throwable);
        }
    }

    public static void e(Class<?> class_, String string2, Object ... arrobject) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(FLog.getTag(class_), FLog.formatString(string2, arrobject));
        }
    }

    public static void e(Class<?> class_, Throwable throwable, String string2, Object ... arrobject) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(FLog.getTag(class_), FLog.formatString(string2, arrobject), throwable);
        }
    }

    public static void e(String string2, String string3) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(string2, string3);
        }
    }

    public static void e(String string2, String string3, Throwable throwable) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(string2, string3, throwable);
        }
    }

    private static String formatString(String string2, Object ... arrobject) {
        return String.format(null, string2, arrobject);
    }

    private static String getTag(Class<?> class_) {
        return class_.getSimpleName();
    }

    public static void i(Class<?> class_, String string2) {
        if (sHandler.isLoggable(4)) {
            sHandler.i(FLog.getTag(class_), string2);
        }
    }

    public static boolean isLoggable(int n) {
        return sHandler.isLoggable(n);
    }

    public static void v(Class<?> class_, String string2) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(FLog.getTag(class_), string2);
        }
    }

    public static void v(Class<?> class_, String string2, Object object) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(FLog.getTag(class_), FLog.formatString(string2, object));
        }
    }

    public static void v(Class<?> class_, String string2, Object object, Object object2) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(FLog.getTag(class_), FLog.formatString(string2, object, object2));
        }
    }

    public static void v(Class<?> class_, String string2, Object object, Object object2, Object object3) {
        if (FLog.isLoggable(2)) {
            FLog.v(class_, FLog.formatString(string2, object, object2, object3));
        }
    }

    public static void v(Class<?> class_, String string2, Object object, Object object2, Object object3, Object object4) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(FLog.getTag(class_), FLog.formatString(string2, object, object2, object3, object4));
        }
    }

    public static void v(Class<?> class_, String string2, Object ... arrobject) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(FLog.getTag(class_), FLog.formatString(string2, arrobject));
        }
    }

    public static void v(String string2, String string3, Object ... arrobject) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(string2, FLog.formatString(string3, arrobject));
        }
    }

    public static void w(Class<?> class_, String string2) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(FLog.getTag(class_), string2);
        }
    }

    public static void w(Class<?> class_, String string2, Throwable throwable) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(FLog.getTag(class_), string2, throwable);
        }
    }

    public static void w(Class<?> class_, String string2, Object ... arrobject) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(FLog.getTag(class_), FLog.formatString(string2, arrobject));
        }
    }

    public static void w(Class<?> class_, Throwable throwable, String string2, Object ... arrobject) {
        if (FLog.isLoggable(5)) {
            FLog.w(class_, FLog.formatString(string2, arrobject), throwable);
        }
    }

    public static void w(String string2, String string3, Object ... arrobject) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(string2, FLog.formatString(string3, arrobject));
        }
    }

    public static void wtf(Class<?> class_, String string2, Throwable throwable) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(FLog.getTag(class_), string2, throwable);
        }
    }

    public static void wtf(Class<?> class_, String string2, Object ... arrobject) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(FLog.getTag(class_), FLog.formatString(string2, arrobject));
        }
    }

    public static void wtf(String string2, String string3, Object ... arrobject) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(string2, FLog.formatString(string3, arrobject));
        }
    }
}

