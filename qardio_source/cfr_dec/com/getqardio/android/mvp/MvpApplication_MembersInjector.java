/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp;

import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.util.RxEventBus;
import com.getqardio.android.provider.SyncHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MvpApplication_MembersInjector
implements MembersInjector<MvpApplication> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<RxEventBus> eventBusProvider;
    private final Provider<FCMManager> mFCMManagerProvider;
    private final Provider<SyncHelper> syncHelperProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !MvpApplication_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public MvpApplication_MembersInjector(Provider<SyncHelper> provider, Provider<RxEventBus> provider2, Provider<FCMManager> provider3) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.syncHelperProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.eventBusProvider = provider2;
        if (!$assertionsDisabled && provider3 == null) {
            throw new AssertionError();
        }
        this.mFCMManagerProvider = provider3;
    }

    public static MembersInjector<MvpApplication> create(Provider<SyncHelper> provider, Provider<RxEventBus> provider2, Provider<FCMManager> provider3) {
        return new MvpApplication_MembersInjector(provider, provider2, provider3);
    }

    @Override
    public void injectMembers(MvpApplication mvpApplication) {
        if (mvpApplication == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        mvpApplication.syncHelper = this.syncHelperProvider.get();
        mvpApplication.eventBus = this.eventBusProvider.get();
        mvpApplication.mFCMManager = this.mFCMManagerProvider.get();
    }
}

