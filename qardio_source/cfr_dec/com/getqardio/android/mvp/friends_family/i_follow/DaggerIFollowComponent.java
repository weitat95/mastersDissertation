/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow;

import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.common.ui.MultiChoiceMode_Factory;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowComponent;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowContract;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenterModule;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenterModule_ProvideItemCheckedCheckerFactory;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenterModule_ProvideViewFactory;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter_Factory;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment_MembersInjector;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowListAdapter;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowListAdapter_Factory;
import com.getqardio.android.provider.WearableDataHelper;
import com.getqardio.android.provider.WearableDataHelper_Factory;
import com.getqardio.android.service.WearableCommunicationService;
import com.getqardio.android.service.WearableCommunicationService_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerIFollowComponent
implements IFollowComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<IFollowUserRepository> getIFollowUserRepositoryProvider;
    private MembersInjector<IFollowFragment> iFollowFragmentMembersInjector;
    private Provider<IFollowListAdapter> iFollowListAdapterProvider;
    private Provider<IFollowPresenter> iFollowPresenterProvider;
    private Provider<ItemCheckedChecker> provideItemCheckedCheckerProvider;
    private Provider<IFollowContract.View> provideViewProvider;
    private MembersInjector<WearableCommunicationService> wearableCommunicationServiceMembersInjector;
    private Provider<WearableDataHelper> wearableDataHelperProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerIFollowComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerIFollowComponent(Builder builder) {
        if (!$assertionsDisabled && builder == null) {
            throw new AssertionError();
        }
        this.initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.getIFollowUserRepositoryProvider = new com_getqardio_android_mvp_common_injection_RepositoryComponent_getIFollowUserRepository(builder.repositoryComponent);
        this.provideViewProvider = IFollowPresenterModule_ProvideViewFactory.create(builder.iFollowPresenterModule);
        this.iFollowPresenterProvider = IFollowPresenter_Factory.create(this.getIFollowUserRepositoryProvider, this.provideViewProvider, MultiChoiceMode_Factory.create());
        this.provideItemCheckedCheckerProvider = IFollowPresenterModule_ProvideItemCheckedCheckerFactory.create(builder.iFollowPresenterModule);
        this.iFollowListAdapterProvider = IFollowListAdapter_Factory.create(MembersInjectors.<IFollowListAdapter>noOp(), this.provideItemCheckedCheckerProvider);
        this.iFollowFragmentMembersInjector = IFollowFragment_MembersInjector.create(this.iFollowPresenterProvider, this.iFollowListAdapterProvider);
        this.wearableDataHelperProvider = WearableDataHelper_Factory.create(this.getIFollowUserRepositoryProvider);
        this.wearableCommunicationServiceMembersInjector = WearableCommunicationService_MembersInjector.create(this.wearableDataHelperProvider);
    }

    @Override
    public void inject(IFollowFragment iFollowFragment) {
        this.iFollowFragmentMembersInjector.injectMembers(iFollowFragment);
    }

    @Override
    public void inject(WearableCommunicationService wearableCommunicationService) {
        this.wearableCommunicationServiceMembersInjector.injectMembers(wearableCommunicationService);
    }

    public static final class Builder {
        private IFollowPresenterModule iFollowPresenterModule;
        private RepositoryComponent repositoryComponent;

        private Builder() {
        }

        public IFollowComponent build() {
            if (this.iFollowPresenterModule == null) {
                this.iFollowPresenterModule = new IFollowPresenterModule();
            }
            if (this.repositoryComponent == null) {
                throw new IllegalStateException(RepositoryComponent.class.getCanonicalName() + " must be set");
            }
            return new DaggerIFollowComponent(this);
        }

        public Builder iFollowPresenterModule(IFollowPresenterModule iFollowPresenterModule) {
            this.iFollowPresenterModule = Preconditions.checkNotNull(iFollowPresenterModule);
            return this;
        }

        public Builder repositoryComponent(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = Preconditions.checkNotNull(repositoryComponent);
            return this;
        }
    }

    private static class com_getqardio_android_mvp_common_injection_RepositoryComponent_getIFollowUserRepository
    implements Provider<IFollowUserRepository> {
        private final RepositoryComponent repositoryComponent;

        com_getqardio_android_mvp_common_injection_RepositoryComponent_getIFollowUserRepository(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = repositoryComponent;
        }

        @Override
        public IFollowUserRepository get() {
            return Preconditions.checkNotNull(this.repositoryComponent.getIFollowUserRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

}

