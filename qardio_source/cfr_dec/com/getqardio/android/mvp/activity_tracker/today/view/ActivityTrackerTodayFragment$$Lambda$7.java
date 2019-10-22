/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.today.view;

import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerTodayFragment$$Lambda$7
implements Consumer {
    private final ActivityTrackerTodayFragment arg$1;

    private ActivityTrackerTodayFragment$$Lambda$7(ActivityTrackerTodayFragment activityTrackerTodayFragment) {
        this.arg$1 = activityTrackerTodayFragment;
    }

    public static Consumer lambdaFactory$(ActivityTrackerTodayFragment activityTrackerTodayFragment) {
        return new ActivityTrackerTodayFragment$$Lambda$7(activityTrackerTodayFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onConnected$4((Integer)object);
    }
}

