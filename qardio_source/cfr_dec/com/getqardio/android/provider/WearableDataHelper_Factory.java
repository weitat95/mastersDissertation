/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.provider;

import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.provider.WearableDataHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class WearableDataHelper_Factory
implements Factory<WearableDataHelper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<IFollowUserRepository> iFollowUserRepositoryProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !WearableDataHelper_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public WearableDataHelper_Factory(Provider<IFollowUserRepository> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.iFollowUserRepositoryProvider = provider;
    }

    public static Factory<WearableDataHelper> create(Provider<IFollowUserRepository> provider) {
        return new WearableDataHelper_Factory(provider);
    }

    @Override
    public WearableDataHelper get() {
        return new WearableDataHelper(this.iFollowUserRepositoryProvider.get());
    }
}

