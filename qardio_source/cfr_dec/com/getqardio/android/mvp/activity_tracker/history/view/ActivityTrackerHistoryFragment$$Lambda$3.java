/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.view;

import android.support.v4.util.Pair;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerHistoryFragment$$Lambda$3
implements Consumer {
    private final ActivityTrackerHistoryFragment arg$1;

    private ActivityTrackerHistoryFragment$$Lambda$3(ActivityTrackerHistoryFragment activityTrackerHistoryFragment) {
        this.arg$1 = activityTrackerHistoryFragment;
    }

    public static Consumer lambdaFactory$(ActivityTrackerHistoryFragment activityTrackerHistoryFragment) {
        return new ActivityTrackerHistoryFragment$$Lambda$3(activityTrackerHistoryFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onConnected$3((Pair)object);
    }
}

