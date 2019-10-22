/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.logger;

import com.crashlytics.android.Crashlytics;
import timber.log.Timber;

public class CrashlyticsTree
extends Timber.Tree {
    @Override
    protected void log(int n, String string2, String string3, Throwable throwable) {
        if (n == 2 || n == 4 || n == 3) {
            return;
        }
        Crashlytics.setInt("priority", n);
        Crashlytics.setString("tag", string2);
        Crashlytics.setString("message", string3);
        if (throwable == null) {
            Crashlytics.logException(new Exception(string3));
            return;
        }
        Crashlytics.logException(throwable);
        Crashlytics.log(n, string2, string3);
    }
}

