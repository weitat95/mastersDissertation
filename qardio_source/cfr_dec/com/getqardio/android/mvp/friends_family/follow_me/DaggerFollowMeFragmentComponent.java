/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me;

import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.common.ui.MultiChoiceMode_Factory;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentComponent;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentContract;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenterModule;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenterModule_ProvideItemCheckedCheckerFactory;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenterModule_ProvideViewFactory;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter_Factory;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment_MembersInjector;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeListAdapter;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeListAdapter_Factory;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerFollowMeFragmentComponent
implements FollowMeFragmentComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private MembersInjector<FollowMeFragment> followMeFragmentMembersInjector;
    private Provider<FollowMeListAdapter> followMeListAdapterProvider;
    private Provider<FollowMePresenter> followMePresenterProvider;
    private Provider<FollowMeUserRepository> getFollowingMeUserRepositoryProvider;
    private Provider<ItemCheckedChecker> provideItemCheckedCheckerProvider;
    private Provider<FollowMeFragmentContract.View> provideViewProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerFollowMeFragmentComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerFollowMeFragmentComponent(Builder builder) {
        if (!$assertionsDisabled && builder == null) {
            throw new AssertionError();
        }
        this.initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.getFollowingMeUserRepositoryProvider = new com_getqardio_android_mvp_common_injection_RepositoryComponent_getFollowingMeUserRepository(builder.repositoryComponent);
        this.provideViewProvider = FollowMePresenterModule_ProvideViewFactory.create(builder.followMePresenterModule);
        this.followMePresenterProvider = FollowMePresenter_Factory.create(this.getFollowingMeUserRepositoryProvider, this.provideViewProvider, MultiChoiceMode_Factory.create());
        this.provideItemCheckedCheckerProvider = FollowMePresenterModule_ProvideItemCheckedCheckerFactory.create(builder.followMePresenterModule);
        this.followMeListAdapterProvider = FollowMeListAdapter_Factory.create(MembersInjectors.<FollowMeListAdapter>noOp(), this.provideItemCheckedCheckerProvider);
        this.followMeFragmentMembersInjector = FollowMeFragment_MembersInjector.create(this.followMePresenterProvider, this.followMeListAdapterProvider);
    }

    @Override
    public void inject(FollowMeFragment followMeFragment) {
        this.followMeFragmentMembersInjector.injectMembers(followMeFragment);
    }

    public static final class Builder {
        private FollowMePresenterModule followMePresenterModule;
        private RepositoryComponent repositoryComponent;

        private Builder() {
        }

        public FollowMeFragmentComponent build() {
            if (this.followMePresenterModule == null) {
                throw new IllegalStateException(FollowMePresenterModule.class.getCanonicalName() + " must be set");
            }
            if (this.repositoryComponent == null) {
                throw new IllegalStateException(RepositoryComponent.class.getCanonicalName() + " must be set");
            }
            return new DaggerFollowMeFragmentComponent(this);
        }

        public Builder followMePresenterModule(FollowMePresenterModule followMePresenterModule) {
            this.followMePresenterModule = Preconditions.checkNotNull(followMePresenterModule);
            return this;
        }

        public Builder repositoryComponent(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = Preconditions.checkNotNull(repositoryComponent);
            return this;
        }
    }

    private static class com_getqardio_android_mvp_common_injection_RepositoryComponent_getFollowingMeUserRepository
    implements Provider<FollowMeUserRepository> {
        private final RepositoryComponent repositoryComponent;

        com_getqardio_android_mvp_common_injection_RepositoryComponent_getFollowingMeUserRepository(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = repositoryComponent;
        }

        @Override
        public FollowMeUserRepository get() {
            return Preconditions.checkNotNull(this.repositoryComponent.getFollowingMeUserRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

}

