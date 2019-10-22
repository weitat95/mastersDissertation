/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.local;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class FollowMeUserLocalDataSource$$Lambda$1
implements SingleOnSubscribe {
    private final FollowMeUserLocalDataSource arg$1;
    private final FollowMeUser arg$2;
    private final User arg$3;
    private final SyncStatus arg$4;

    private FollowMeUserLocalDataSource$$Lambda$1(FollowMeUserLocalDataSource followMeUserLocalDataSource, FollowMeUser followMeUser, User user, SyncStatus syncStatus) {
        this.arg$1 = followMeUserLocalDataSource;
        this.arg$2 = followMeUser;
        this.arg$3 = user;
        this.arg$4 = syncStatus;
    }

    public static SingleOnSubscribe lambdaFactory$(FollowMeUserLocalDataSource followMeUserLocalDataSource, FollowMeUser followMeUser, User user, SyncStatus syncStatus) {
        return new FollowMeUserLocalDataSource$$Lambda$1(followMeUserLocalDataSource, followMeUser, user, syncStatus);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        this.arg$1.lambda$inviteOrSaveUserToFollowMe$1(this.arg$2, this.arg$3, this.arg$4, singleEmitter);
    }
}

