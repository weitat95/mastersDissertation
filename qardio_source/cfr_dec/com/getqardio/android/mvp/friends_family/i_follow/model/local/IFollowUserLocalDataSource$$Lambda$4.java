/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource;
import io.reactivex.SingleEmitter;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;

final class IFollowUserLocalDataSource$$Lambda$4
implements Realm.Transaction {
    private final IFollowUserLocalDataSource arg$1;
    private final IFollowUser arg$2;
    private final SyncStatus arg$3;
    private final SingleEmitter arg$4;

    private IFollowUserLocalDataSource$$Lambda$4(IFollowUserLocalDataSource iFollowUserLocalDataSource, IFollowUser iFollowUser, SyncStatus syncStatus, SingleEmitter singleEmitter) {
        this.arg$1 = iFollowUserLocalDataSource;
        this.arg$2 = iFollowUser;
        this.arg$3 = syncStatus;
        this.arg$4 = singleEmitter;
    }

    public static Realm.Transaction lambdaFactory$(IFollowUserLocalDataSource iFollowUserLocalDataSource, IFollowUser iFollowUser, SyncStatus syncStatus, SingleEmitter singleEmitter) {
        return new IFollowUserLocalDataSource$$Lambda$4(iFollowUserLocalDataSource, iFollowUser, syncStatus, singleEmitter);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        this.arg$1.lambda$null$0(this.arg$2, this.arg$3, this.arg$4, realm);
    }
}

