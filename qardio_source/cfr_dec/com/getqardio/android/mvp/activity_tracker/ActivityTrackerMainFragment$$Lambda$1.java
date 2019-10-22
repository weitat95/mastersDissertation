/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.mvp.activity_tracker;

import android.view.View;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerMainFragment$$Lambda$1
implements View.OnClickListener {
    private final ActivityTrackerMainFragment arg$1;

    private ActivityTrackerMainFragment$$Lambda$1(ActivityTrackerMainFragment activityTrackerMainFragment) {
        this.arg$1 = activityTrackerMainFragment;
    }

    public static View.OnClickListener lambdaFactory$(ActivityTrackerMainFragment activityTrackerMainFragment) {
        return new ActivityTrackerMainFragment$$Lambda$1(activityTrackerMainFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreateView$0(view);
    }
}

