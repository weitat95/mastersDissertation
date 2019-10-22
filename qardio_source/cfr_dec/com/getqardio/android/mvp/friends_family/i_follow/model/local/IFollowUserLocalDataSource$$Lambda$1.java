/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class IFollowUserLocalDataSource$$Lambda$1
implements SingleOnSubscribe {
    private final IFollowUserLocalDataSource arg$1;
    private final IFollowUser arg$2;
    private final SyncStatus arg$3;

    private IFollowUserLocalDataSource$$Lambda$1(IFollowUserLocalDataSource iFollowUserLocalDataSource, IFollowUser iFollowUser, SyncStatus syncStatus) {
        this.arg$1 = iFollowUserLocalDataSource;
        this.arg$2 = iFollowUser;
        this.arg$3 = syncStatus;
    }

    public static SingleOnSubscribe lambdaFactory$(IFollowUserLocalDataSource iFollowUserLocalDataSource, IFollowUser iFollowUser, SyncStatus syncStatus) {
        return new IFollowUserLocalDataSource$$Lambda$1(iFollowUserLocalDataSource, iFollowUser, syncStatus);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        this.arg$1.lambda$saveIFollowUser$1(this.arg$2, this.arg$3, singleEmitter);
    }
}

