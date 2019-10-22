/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.remote.ApiResponseException;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository$$Lambda$1;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository$$Lambda$10;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository$$Lambda$2;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository$$Lambda$5;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository$$Lambda$8;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository$$Lambda$9;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import java.util.List;

public class IFollowUserRepository
implements IFollowUserDataSource {
    private final IFollowUserDataSource localDataSource;
    private final IFollowUserDataSource remoteDataSource;

    public IFollowUserRepository(IFollowUserDataSource iFollowUserDataSource, IFollowUserDataSource iFollowUserDataSource2) {
        this.localDataSource = iFollowUserDataSource;
        this.remoteDataSource = iFollowUserDataSource2;
    }

    private Maybe<List<IFollowUser>> getRemotelyAndSave(long l) {
        return this.remoteDataSource.getIFollowUsersMaybe(l).compose(RxUtil.applyMaybeSchedulers()).flatMap(IFollowUserRepository$$Lambda$10.lambdaFactory$(this, l));
    }

    private Single<IFollowUser> syncDelete(long l, IFollowUser iFollowUser) {
        return this.remoteDataSource.deleteIFollowUser(l, iFollowUser).compose(RxUtil.applySingleSchedulers()).flatMap(IFollowUserRepository$$Lambda$8.lambdaFactory$(this, l, iFollowUser)).onErrorResumeNext(IFollowUserRepository$$Lambda$9.lambdaFactory$(this, l, iFollowUser));
    }

    @Override
    public Single<IFollowUser> deleteIFollowUser(long l, IFollowUser iFollowUser) {
        return this.localDataSource.saveIFollowUser(l, iFollowUser, SyncStatus.NEED_DELETE).flatMap(IFollowUserRepository$$Lambda$2.lambdaFactory$(this, l, iFollowUser));
    }

    @Override
    public Single<IFollowUser> enablePushNotifications(long l, IFollowUser iFollowUser, boolean bl) {
        return this.remoteDataSource.enablePushNotifications(l, iFollowUser, bl).compose(RxUtil.applySingleSchedulers()).flatMap(IFollowUserRepository$$Lambda$5.lambdaFactory$(this, iFollowUser, bl, l));
    }

    public Flowable<List<IFollowUser>> getIFollowUsers(long l) {
        return this.localDataSource.getIFollowUsersMaybe(l).mergeWith(this.getRemotelyAndSave(l).flatMap(IFollowUserRepository$$Lambda$1.lambdaFactory$(this, l)));
    }

    @Override
    public Maybe<List<IFollowUser>> getIFollowUsersMaybe(long l) {
        return this.localDataSource.getIFollowUsersMaybe(l);
    }

    /* synthetic */ SingleSource lambda$deleteIFollowUser$1(long l, IFollowUser iFollowUser, IFollowUser iFollowUser2) throws Exception {
        return this.syncDelete(l, iFollowUser);
    }

    /* synthetic */ SingleSource lambda$enablePushNotifications$4(IFollowUser iFollowUser, boolean bl, long l, IFollowUser iFollowUser2) throws Exception {
        iFollowUser.setNotificationsEnabled(bl);
        return this.localDataSource.saveIFollowUser(l, iFollowUser, iFollowUser.getSyncStatus());
    }

    /* synthetic */ MaybeSource lambda$getIFollowUsers$0(long l, List list) throws Exception {
        return this.localDataSource.getIFollowUsersMaybe(l);
    }

    /* synthetic */ MaybeSource lambda$getRemotelyAndSave$9(long l, List list) throws Exception {
        return this.localDataSource.rewriteIFollowUsers(l, list);
    }

    /* synthetic */ SingleSource lambda$syncDelete$7(long l, IFollowUser iFollowUser, IFollowUser iFollowUser2) throws Exception {
        return this.localDataSource.deleteIFollowUser(l, iFollowUser);
    }

    /* synthetic */ SingleSource lambda$syncDelete$8(long l, IFollowUser iFollowUser, Throwable throwable) throws Exception {
        if (throwable instanceof ApiResponseException) {
            return this.localDataSource.deleteIFollowUser(l, iFollowUser);
        }
        return Single.just(iFollowUser);
    }

    @Override
    public Maybe<List<IFollowUser>> rewriteIFollowUsers(long l, List<IFollowUser> list) {
        return this.localDataSource.rewriteIFollowUsers(l, list);
    }

    @Override
    public Single<IFollowUser> saveIFollowUser(long l, IFollowUser iFollowUser, SyncStatus syncStatus) {
        throw new UnsupportedOperationException();
    }
}

