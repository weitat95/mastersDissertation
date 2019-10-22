/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.local;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource;
import io.reactivex.SingleEmitter;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;

final class FollowMeUserLocalDataSource$$Lambda$4
implements Realm.Transaction {
    private final FollowMeUserLocalDataSource arg$1;
    private final FollowMeUser arg$2;
    private final User arg$3;
    private final SyncStatus arg$4;
    private final SingleEmitter arg$5;

    private FollowMeUserLocalDataSource$$Lambda$4(FollowMeUserLocalDataSource followMeUserLocalDataSource, FollowMeUser followMeUser, User user, SyncStatus syncStatus, SingleEmitter singleEmitter) {
        this.arg$1 = followMeUserLocalDataSource;
        this.arg$2 = followMeUser;
        this.arg$3 = user;
        this.arg$4 = syncStatus;
        this.arg$5 = singleEmitter;
    }

    public static Realm.Transaction lambdaFactory$(FollowMeUserLocalDataSource followMeUserLocalDataSource, FollowMeUser followMeUser, User user, SyncStatus syncStatus, SingleEmitter singleEmitter) {
        return new FollowMeUserLocalDataSource$$Lambda$4(followMeUserLocalDataSource, followMeUser, user, syncStatus, singleEmitter);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        this.arg$1.lambda$null$0(this.arg$2, this.arg$3, this.arg$4, this.arg$5, realm);
    }
}

