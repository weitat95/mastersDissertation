/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me;

import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogComponent;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogContract;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenterModule;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenterModule_ProvideViewFactory;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter_Factory;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerFollowMeAddFollowingDialogComponent
implements FollowMeAddFollowingDialogComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private MembersInjector<FollowMeAddFollowingDialog> followMeAddFollowingDialogMembersInjector;
    private Provider<FollowMeAddFollowingDialogPresenter> followMeAddFollowingDialogPresenterProvider;
    private Provider<FollowMeUserRepository> getFollowingMeUserRepositoryProvider;
    private Provider<FollowMeAddFollowingDialogContract.View> provideViewProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerFollowMeAddFollowingDialogComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerFollowMeAddFollowingDialogComponent(Builder builder) {
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
        this.provideViewProvider = FollowMeAddFollowingDialogPresenterModule_ProvideViewFactory.create(builder.followMeAddFollowingDialogPresenterModule);
        this.followMeAddFollowingDialogPresenterProvider = FollowMeAddFollowingDialogPresenter_Factory.create(this.getFollowingMeUserRepositoryProvider, this.provideViewProvider);
        this.followMeAddFollowingDialogMembersInjector = FollowMeAddFollowingDialog_MembersInjector.create(this.followMeAddFollowingDialogPresenterProvider);
    }

    @Override
    public void inject(FollowMeAddFollowingDialog followMeAddFollowingDialog) {
        this.followMeAddFollowingDialogMembersInjector.injectMembers(followMeAddFollowingDialog);
    }

    public static final class Builder {
        private FollowMeAddFollowingDialogPresenterModule followMeAddFollowingDialogPresenterModule;
        private RepositoryComponent repositoryComponent;

        private Builder() {
        }

        public FollowMeAddFollowingDialogComponent build() {
            if (this.followMeAddFollowingDialogPresenterModule == null) {
                throw new IllegalStateException(FollowMeAddFollowingDialogPresenterModule.class.getCanonicalName() + " must be set");
            }
            if (this.repositoryComponent == null) {
                throw new IllegalStateException(RepositoryComponent.class.getCanonicalName() + " must be set");
            }
            return new DaggerFollowMeAddFollowingDialogComponent(this);
        }

        public Builder followMeAddFollowingDialogPresenterModule(FollowMeAddFollowingDialogPresenterModule followMeAddFollowingDialogPresenterModule) {
            this.followMeAddFollowingDialogPresenterModule = Preconditions.checkNotNull(followMeAddFollowingDialogPresenterModule);
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

