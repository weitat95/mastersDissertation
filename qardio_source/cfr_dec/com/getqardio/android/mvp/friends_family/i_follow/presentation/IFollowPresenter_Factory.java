/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.common.ui.MultiChoiceMode;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowContract;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class IFollowPresenter_Factory
implements Factory<IFollowPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<MultiChoiceMode> multiChoiceModeProvider;
    private final Provider<IFollowUserRepository> repositoryProvider;
    private final Provider<IFollowContract.View> viewProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !IFollowPresenter_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public IFollowPresenter_Factory(Provider<IFollowUserRepository> provider, Provider<IFollowContract.View> provider2, Provider<MultiChoiceMode> provider3) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.repositoryProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.viewProvider = provider2;
        if (!$assertionsDisabled && provider3 == null) {
            throw new AssertionError();
        }
        this.multiChoiceModeProvider = provider3;
    }

    public static Factory<IFollowPresenter> create(Provider<IFollowUserRepository> provider, Provider<IFollowContract.View> provider2, Provider<MultiChoiceMode> provider3) {
        return new IFollowPresenter_Factory(provider, provider2, provider3);
    }

    @Override
    public IFollowPresenter get() {
        return new IFollowPresenter(this.repositoryProvider.get(), this.viewProvider.get(), this.multiChoiceModeProvider.get());
    }
}

