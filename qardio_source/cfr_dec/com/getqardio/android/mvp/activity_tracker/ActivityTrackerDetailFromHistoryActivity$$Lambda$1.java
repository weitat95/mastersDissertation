/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker;

import com.getqardio.android.mvp.activity_tracker.ActivityTrackerDetailFromHistoryActivity;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerDetailFromHistoryActivity$$Lambda$1
implements Consumer {
    private static final ActivityTrackerDetailFromHistoryActivity$$Lambda$1 instance = new ActivityTrackerDetailFromHistoryActivity$$Lambda$1();

    private ActivityTrackerDetailFromHistoryActivity$$Lambda$1() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        ActivityTrackerDetailFromHistoryActivity.lambda$onConnected$0((Throwable)object);
    }
}

