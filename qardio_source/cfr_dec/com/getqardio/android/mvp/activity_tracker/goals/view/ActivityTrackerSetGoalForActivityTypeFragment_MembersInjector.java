/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.view;

import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenter;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackerSetGoalForActivityTypeFragment;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ActivityTrackerSetGoalForActivityTypeFragment_MembersInjector
implements MembersInjector<ActivityTrackerSetGoalForActivityTypeFragment> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<SetGoalForActivityTypePresenter> presenterProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ActivityTrackerSetGoalForActivityTypeFragment_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public ActivityTrackerSetGoalForActivityTypeFragment_MembersInjector(Provider<SetGoalForActivityTypePresenter> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.presenterProvider = provider;
    }

    public static MembersInjector<ActivityTrackerSetGoalForActivityTypeFragment> create(Provider<SetGoalForActivityTypePresenter> provider) {
        return new ActivityTrackerSetGoalForActivityTypeFragment_MembersInjector(provider);
    }

    @Override
    public void injectMembers(ActivityTrackerSetGoalForActivityTypeFragment activityTrackerSetGoalForActivityTypeFragment) {
        if (activityTrackerSetGoalForActivityTypeFragment == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        activityTrackerSetGoalForActivityTypeFragment.presenter = this.presenterProvider.get();
    }
}

