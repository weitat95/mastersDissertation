/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.local;

import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;

final class FollowMeUserLocalDataSource$$Lambda$2
implements Realm.Transaction {
    private final FollowMeUserLocalDataSource arg$1;
    private final long arg$2;
    private final FollowMeUser arg$3;

    private FollowMeUserLocalDataSource$$Lambda$2(FollowMeUserLocalDataSource followMeUserLocalDataSource, long l, FollowMeUser followMeUser) {
        this.arg$1 = followMeUserLocalDataSource;
        this.arg$2 = l;
        this.arg$3 = followMeUser;
    }

    public static Realm.Transaction lambdaFactory$(FollowMeUserLocalDataSource followMeUserLocalDataSource, long l, FollowMeUser followMeUser) {
        return new FollowMeUserLocalDataSource$$Lambda$2(followMeUserLocalDataSource, l, followMeUser);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        this.arg$1.lambda$deleteFollowMeUser$2(this.arg$2, this.arg$3, realm);
    }
}

