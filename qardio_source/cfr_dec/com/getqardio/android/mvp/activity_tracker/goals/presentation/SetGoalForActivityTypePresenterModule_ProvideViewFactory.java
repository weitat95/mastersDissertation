/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.presentation;

import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityContract;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenterModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class SetGoalForActivityTypePresenterModule_ProvideViewFactory
implements Factory<GoalActivityContract.View> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final SetGoalForActivityTypePresenterModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !SetGoalForActivityTypePresenterModule_ProvideViewFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public SetGoalForActivityTypePresenterModule_ProvideViewFactory(SetGoalForActivityTypePresenterModule setGoalForActivityTypePresenterModule) {
        if (!$assertionsDisabled && setGoalForActivityTypePresenterModule == null) {
            throw new AssertionError();
        }
        this.module = setGoalForActivityTypePresenterModule;
    }

    public static Factory<GoalActivityContract.View> create(SetGoalForActivityTypePresenterModule setGoalForActivityTypePresenterModule) {
        return new SetGoalForActivityTypePresenterModule_ProvideViewFactory(setGoalForActivityTypePresenterModule);
    }

    @Override
    public GoalActivityContract.View get() {
        return Preconditions.checkNotNull(this.module.provideView(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

