/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.crashlytics.android.answers.shim;

import android.util.Log;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.shim.KitEvent;
import com.crashlytics.android.answers.shim.KitEventLogger;

class AnswersKitEventLogger
implements KitEventLogger {
    private final Answers answers;

    private AnswersKitEventLogger(Answers answers) {
        this.answers = answers;
    }

    public static AnswersKitEventLogger create() throws NoClassDefFoundError, IllegalStateException {
        return AnswersKitEventLogger.create(Answers.getInstance());
    }

    static AnswersKitEventLogger create(Answers answers) throws IllegalStateException {
        if (answers == null) {
            throw new IllegalStateException("Answers must be initialized before logging kit events");
        }
        return new AnswersKitEventLogger(answers);
    }

    @Override
    public void logKitEvent(KitEvent kitEvent) {
        try {
            this.answers.logCustom(kitEvent.toCustomEvent());
            return;
        }
        catch (Throwable throwable) {
            Log.w((String)"AnswersKitEventLogger", (String)"Unexpected error sending Answers event", (Throwable)throwable);
            return;
        }
    }
}

