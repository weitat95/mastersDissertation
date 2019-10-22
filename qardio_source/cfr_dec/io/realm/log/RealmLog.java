/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package io.realm.log;

import android.util.Log;
import io.realm.log.RealmLogger;
import java.util.Locale;

public final class RealmLog {
    private static String REALM_JAVA_TAG = "REALM_JAVA";

    public static void debug(String string2, Object ... arrobject) {
        RealmLog.debug(null, string2, arrobject);
    }

    public static void debug(Throwable throwable, String string2, Object ... arrobject) {
        RealmLog.log(3, throwable, string2, arrobject);
    }

    public static void fatal(String string2, Object ... arrobject) {
        RealmLog.fatal(null, string2, arrobject);
    }

    public static void fatal(Throwable throwable, String string2, Object ... arrobject) {
        RealmLog.log(7, throwable, string2, arrobject);
    }

    public static int getLevel() {
        return RealmLog.nativeGetLogLevel();
    }

    private static void log(int n, Throwable throwable, String string2, Object ... arrobject) {
        if (n < RealmLog.getLevel()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string3 = string2;
        if (arrobject != null) {
            string3 = string2;
            if (arrobject.length > 0) {
                string3 = String.format(Locale.US, string2, arrobject);
            }
        }
        if (throwable != null) {
            stringBuilder.append(Log.getStackTraceString((Throwable)throwable));
        }
        if (string3 != null) {
            if (throwable != null) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(string3);
        }
        RealmLog.nativeLog(n, REALM_JAVA_TAG, throwable, stringBuilder.toString());
    }

    private static native void nativeAddLogger(RealmLogger var0);

    private static native void nativeClearLoggers();

    static native void nativeCloseCoreLoggerBridge(long var0);

    static native long nativeCreateCoreLoggerBridge(String var0);

    private static native int nativeGetLogLevel();

    private static native void nativeLog(int var0, String var1, Throwable var2, String var3);

    static native void nativeLogToCoreLoggerBridge(long var0, int var2, String var3);

    private static native void nativeRegisterDefaultLogger();

    private static native void nativeRemoveLogger(RealmLogger var0);

    private static native void nativeSetLogLevel(int var0);

    public static void warn(String string2, Object ... arrobject) {
        RealmLog.warn(null, string2, arrobject);
    }

    public static void warn(Throwable throwable, String string2, Object ... arrobject) {
        RealmLog.log(5, throwable, string2, arrobject);
    }
}

