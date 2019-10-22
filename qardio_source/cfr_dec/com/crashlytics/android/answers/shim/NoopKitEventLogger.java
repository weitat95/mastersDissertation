/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers.shim;

import com.crashlytics.android.answers.shim.KitEvent;
import com.crashlytics.android.answers.shim.KitEventLogger;

class NoopKitEventLogger
implements KitEventLogger {
    NoopKitEventLogger() {
    }

    public static KitEventLogger create() {
        return new NoopKitEventLogger();
    }

    @Override
    public void logKitEvent(KitEvent kitEvent) {
    }
}

