/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;

final class IFollowUserLocalDataSource$$Lambda$2
implements Realm.Transaction {
    private final IFollowUser arg$1;

    private IFollowUserLocalDataSource$$Lambda$2(IFollowUser iFollowUser) {
        this.arg$1 = iFollowUser;
    }

    public static Realm.Transaction lambdaFactory$(IFollowUser iFollowUser) {
        return new IFollowUserLocalDataSource$$Lambda$2(iFollowUser);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        IFollowUserLocalDataSource.lambda$deleteIFollowUser$2(this.arg$1, realm);
    }
}

