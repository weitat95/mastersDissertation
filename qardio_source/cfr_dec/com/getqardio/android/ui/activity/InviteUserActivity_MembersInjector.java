/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.activity;

import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.ui.activity.InviteUserActivity;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class InviteUserActivity_MembersInjector
implements MembersInjector<InviteUserActivity> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<InviteApi> inviteApiProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !InviteUserActivity_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public InviteUserActivity_MembersInjector(Provider<InviteApi> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.inviteApiProvider = provider;
    }

    public static MembersInjector<InviteUserActivity> create(Provider<InviteApi> provider) {
        return new InviteUserActivity_MembersInjector(provider);
    }

    @Override
    public void injectMembers(InviteUserActivity inviteUserActivity) {
        if (inviteUserActivity == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        inviteUserActivity.inviteApi = this.inviteApiProvider.get();
    }
}

