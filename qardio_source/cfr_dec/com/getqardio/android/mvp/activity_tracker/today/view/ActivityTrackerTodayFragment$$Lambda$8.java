/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.today.view;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class ActivityTrackerTodayFragment$$Lambda$8
implements Consumer {
    private static final ActivityTrackerTodayFragment$$Lambda$8 instance = new ActivityTrackerTodayFragment$$Lambda$8();

    private ActivityTrackerTodayFragment$$Lambda$8() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

