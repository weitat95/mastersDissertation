/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker;

import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerMainFragment$$Lambda$2
implements Consumer {
    private final ActivityTrackerMainFragment arg$1;

    private ActivityTrackerMainFragment$$Lambda$2(ActivityTrackerMainFragment activityTrackerMainFragment) {
        this.arg$1 = activityTrackerMainFragment;
    }

    public static Consumer lambdaFactory$(ActivityTrackerMainFragment activityTrackerMainFragment) {
        return new ActivityTrackerMainFragment$$Lambda$2(activityTrackerMainFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onConnected$1((Boolean)object);
    }
}

