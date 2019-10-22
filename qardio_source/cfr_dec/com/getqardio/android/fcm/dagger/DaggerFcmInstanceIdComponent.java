/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm.dagger;

import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.fcm.QardioFirebaseInstanceIDService;
import com.getqardio.android.fcm.QardioFirebaseInstanceIDService_MembersInjector;
import com.getqardio.android.fcm.dagger.FCMModule;
import com.getqardio.android.fcm.dagger.FCMModule_ProvideFCMManagerFactory;
import com.getqardio.android.fcm.dagger.FcmInstanceIdComponent;
import dagger.MembersInjector;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerFcmInstanceIdComponent
implements FcmInstanceIdComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<FCMManager> provideFCMManagerProvider;
    private MembersInjector<QardioFirebaseInstanceIDService> qardioFirebaseInstanceIDServiceMembersInjector;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerFcmInstanceIdComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerFcmInstanceIdComponent(Builder builder) {
        if (!$assertionsDisabled && builder == null) {
            throw new AssertionError();
        }
        this.initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.provideFCMManagerProvider = FCMModule_ProvideFCMManagerFactory.create(builder.fCMModule);
        this.qardioFirebaseInstanceIDServiceMembersInjector = QardioFirebaseInstanceIDService_MembersInjector.create(this.provideFCMManagerProvider);
    }

    @Override
    public void inject(QardioFirebaseInstanceIDService qardioFirebaseInstanceIDService) {
        this.qardioFirebaseInstanceIDServiceMembersInjector.injectMembers(qardioFirebaseInstanceIDService);
    }

    public static final class Builder {
        private FCMModule fCMModule;

        private Builder() {
        }

        public FcmInstanceIdComponent build() {
            if (this.fCMModule == null) {
                throw new IllegalStateException(FCMModule.class.getCanonicalName() + " must be set");
            }
            return new DaggerFcmInstanceIdComponent(this);
        }

        public Builder fCMModule(FCMModule fCMModule) {
            this.fCMModule = Preconditions.checkNotNull(fCMModule);
            return this;
        }
    }

}

