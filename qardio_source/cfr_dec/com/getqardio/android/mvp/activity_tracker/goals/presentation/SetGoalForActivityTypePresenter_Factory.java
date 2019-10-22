/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.presentation;

import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityContract;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeRepository;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenter;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SetGoalForActivityTypePresenter_Factory
implements Factory<SetGoalForActivityTypePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<GoalForActivityTypeRepository> repositoryProvider;
    private final Provider<GoalActivityContract.View> viewProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !SetGoalForActivityTypePresenter_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public SetGoalForActivityTypePresenter_Factory(Provider<GoalForActivityTypeRepository> provider, Provider<GoalActivityContract.View> provider2) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.repositoryProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.viewProvider = provider2;
    }

    public static Factory<SetGoalForActivityTypePresenter> create(Provider<GoalForActivityTypeRepository> provider, Provider<GoalActivityContract.View> provider2) {
        return new SetGoalForActivityTypePresenter_Factory(provider, provider2);
    }

    @Override
    public SetGoalForActivityTypePresenter get() {
        return new SetGoalForActivityTypePresenter(this.repositoryProvider.get(), this.viewProvider.get());
    }
}

