/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.crashlytics.android.answers.shim;

import android.util.Log;
import com.crashlytics.android.answers.shim.AnswersKitEventLogger;
import com.crashlytics.android.answers.shim.KitEventLogger;
import com.crashlytics.android.answers.shim.NoopKitEventLogger;

public class AnswersOptionalLogger {
    private static final KitEventLogger logger;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        KitEventLogger kitEventLogger = null;
        try {
            AnswersKitEventLogger answersKitEventLogger;
            kitEventLogger = answersKitEventLogger = AnswersKitEventLogger.create();
        }
        catch (Throwable throwable) {
            Log.w((String)"AnswersOptionalLogger", (String)"Unexpected error creating AnswersKitEventLogger", (Throwable)throwable);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
        }
        catch (IllegalStateException illegalStateException) {}
        if (kitEventLogger == null) {
            kitEventLogger = NoopKitEventLogger.create();
        }
        logger = kitEventLogger;
    }

    public static KitEventLogger get() {
        return logger;
    }
}

