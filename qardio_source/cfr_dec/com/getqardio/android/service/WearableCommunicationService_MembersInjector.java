/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.service;

import com.getqardio.android.provider.WearableDataHelper;
import com.getqardio.android.service.WearableCommunicationService;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class WearableCommunicationService_MembersInjector
implements MembersInjector<WearableCommunicationService> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<WearableDataHelper> wearableDataHelperProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !WearableCommunicationService_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public WearableCommunicationService_MembersInjector(Provider<WearableDataHelper> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.wearableDataHelperProvider = provider;
    }

    public static MembersInjector<WearableCommunicationService> create(Provider<WearableDataHelper> provider) {
        return new WearableCommunicationService_MembersInjector(provider);
    }

    @Override
    public void injectMembers(WearableCommunicationService wearableCommunicationService) {
        if (wearableCommunicationService == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        wearableCommunicationService.wearableDataHelper = this.wearableDataHelperProvider.get();
    }
}

