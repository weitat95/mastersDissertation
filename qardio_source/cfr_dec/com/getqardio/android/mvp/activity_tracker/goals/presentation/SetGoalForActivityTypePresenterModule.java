/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.presentation;

import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityContract;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackerSetGoalForActivityTypeFragment;

public class SetGoalForActivityTypePresenterModule {
    private final ActivityTrackerSetGoalForActivityTypeFragment view;

    public SetGoalForActivityTypePresenterModule() {
        this.view = null;
    }

    public SetGoalForActivityTypePresenterModule(ActivityTrackerSetGoalForActivityTypeFragment activityTrackerSetGoalForActivityTypeFragment) {
        this.view = activityTrackerSetGoalForActivityTypeFragment;
    }

    GoalActivityContract.View provideView() {
        return this.view;
    }
}

