/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals;

import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityComponent;
import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityContract;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeRepository;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenter;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenterModule;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenterModule_ProvideViewFactory;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenter_Factory;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackerSetGoalForActivityTypeFragment;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackerSetGoalForActivityTypeFragment_MembersInjector;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import dagger.MembersInjector;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerGoalActivityComponent
implements GoalActivityComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private MembersInjector<ActivityTrackerSetGoalForActivityTypeFragment> activityTrackerSetGoalForActivityTypeFragmentMembersInjector;
    private Provider<GoalForActivityTypeRepository> getGoalForActivityTypeRepositoryProvider;
    private Provider<GoalActivityContract.View> provideViewProvider;
    private Provider<SetGoalForActivityTypePresenter> setGoalForActivityTypePresenterProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerGoalActivityComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerGoalActivityComponent(Builder builder) {
        if (!$assertionsDisabled && builder == null) {
            throw new AssertionError();
        }
        this.initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.getGoalForActivityTypeRepositoryProvider = new com_getqardio_android_mvp_common_injection_RepositoryComponent_getGoalForActivityTypeRepository(builder.repositoryComponent);
        this.provideViewProvider = SetGoalForActivityTypePresenterModule_ProvideViewFactory.create(builder.setGoalForActivityTypePresenterModule);
        this.setGoalForActivityTypePresenterProvider = SetGoalForActivityTypePresenter_Factory.create(this.getGoalForActivityTypeRepositoryProvider, this.provideViewProvider);
        this.activityTrackerSetGoalForActivityTypeFragmentMembersInjector = ActivityTrackerSetGoalForActivityTypeFragment_MembersInjector.create(this.setGoalForActivityTypePresenterProvider);
    }

    @Override
    public void inject(ActivityTrackerSetGoalForActivityTypeFragment activityTrackerSetGoalForActivityTypeFragment) {
        this.activityTrackerSetGoalForActivityTypeFragmentMembersInjector.injectMembers(activityTrackerSetGoalForActivityTypeFragment);
    }

    public static final class Builder {
        private RepositoryComponent repositoryComponent;
        private SetGoalForActivityTypePresenterModule setGoalForActivityTypePresenterModule;

        private Builder() {
        }

        public GoalActivityComponent build() {
            if (this.setGoalForActivityTypePresenterModule == null) {
                this.setGoalForActivityTypePresenterModule = new SetGoalForActivityTypePresenterModule();
            }
            if (this.repositoryComponent == null) {
                throw new IllegalStateException(RepositoryComponent.class.getCanonicalName() + " must be set");
            }
            return new DaggerGoalActivityComponent(this);
        }

        public Builder repositoryComponent(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = Preconditions.checkNotNull(repositoryComponent);
            return this;
        }

        public Builder setGoalForActivityTypePresenterModule(SetGoalForActivityTypePresenterModule setGoalForActivityTypePresenterModule) {
            this.setGoalForActivityTypePresenterModule = Preconditions.checkNotNull(setGoalForActivityTypePresenterModule);
            return this;
        }
    }

    private static class com_getqardio_android_mvp_common_injection_RepositoryComponent_getGoalForActivityTypeRepository
    implements Provider<GoalForActivityTypeRepository> {
        private final RepositoryComponent repositoryComponent;

        com_getqardio_android_mvp_common_injection_RepositoryComponent_getGoalForActivityTypeRepository(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = repositoryComponent;
        }

        @Override
        public GoalForActivityTypeRepository get() {
            return Preconditions.checkNotNull(this.repositoryComponent.getGoalForActivityTypeRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

}

