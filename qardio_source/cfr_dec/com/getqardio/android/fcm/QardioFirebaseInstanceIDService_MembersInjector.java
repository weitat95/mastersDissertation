/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm;

import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.fcm.QardioFirebaseInstanceIDService;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class QardioFirebaseInstanceIDService_MembersInjector
implements MembersInjector<QardioFirebaseInstanceIDService> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<FCMManager> fcmManagerProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !QardioFirebaseInstanceIDService_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public QardioFirebaseInstanceIDService_MembersInjector(Provider<FCMManager> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.fcmManagerProvider = provider;
    }

    public static MembersInjector<QardioFirebaseInstanceIDService> create(Provider<FCMManager> provider) {
        return new QardioFirebaseInstanceIDService_MembersInjector(provider);
    }

    @Override
    public void injectMembers(QardioFirebaseInstanceIDService qardioFirebaseInstanceIDService) {
        if (qardioFirebaseInstanceIDService == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        qardioFirebaseInstanceIDService.fcmManager = this.fcmManagerProvider.get();
    }
}

