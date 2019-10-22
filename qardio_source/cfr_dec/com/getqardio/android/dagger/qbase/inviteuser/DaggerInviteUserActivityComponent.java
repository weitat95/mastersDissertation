/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.dagger.qbase.inviteuser;

import com.getqardio.android.dagger.qbase.inviteuser.InviteUserActivityComponent;
import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.ui.activity.InviteUserActivity;
import com.getqardio.android.ui.activity.InviteUserActivity_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerInviteUserActivityComponent
implements InviteUserActivityComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private MembersInjector<InviteUserActivity> inviteUserActivityMembersInjector;
    private Provider<InviteApi> provideInviteApiProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerInviteUserActivityComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerInviteUserActivityComponent(Builder builder) {
        if (!$assertionsDisabled && builder == null) {
            throw new AssertionError();
        }
        this.initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.provideInviteApiProvider = new com_getqardio_android_mvp_common_injection_RepositoryComponent_provideInviteApi(builder.repositoryComponent);
        this.inviteUserActivityMembersInjector = InviteUserActivity_MembersInjector.create(this.provideInviteApiProvider);
    }

    @Override
    public void inject(InviteUserActivity inviteUserActivity) {
        this.inviteUserActivityMembersInjector.injectMembers(inviteUserActivity);
    }

    public static final class Builder {
        private RepositoryComponent repositoryComponent;

        private Builder() {
        }

        public InviteUserActivityComponent build() {
            if (this.repositoryComponent == null) {
                throw new IllegalStateException(RepositoryComponent.class.getCanonicalName() + " must be set");
            }
            return new DaggerInviteUserActivityComponent(this);
        }

        public Builder repositoryComponent(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = Preconditions.checkNotNull(repositoryComponent);
            return this;
        }
    }

    private static class com_getqardio_android_mvp_common_injection_RepositoryComponent_provideInviteApi
    implements Provider<InviteApi> {
        private final RepositoryComponent repositoryComponent;

        com_getqardio_android_mvp_common_injection_RepositoryComponent_provideInviteApi(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = repositoryComponent;
        }

        @Override
        public InviteApi get() {
            return Preconditions.checkNotNull(this.repositoryComponent.provideInviteApi(), "Cannot return null from a non-@Nullable component method");
        }
    }

}

