/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.remote.ApiResponseException;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$10;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$11;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$12;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$2;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$3;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$8;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository$$Lambda$9;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.List;

public class FollowMeUserRepository
implements FollowMeUserDataSource {
    private final FollowMeUserDataSource localDataSource;
    private final FollowMeUserDataSource remoteDataSource;

    public FollowMeUserRepository(FollowMeUserDataSource followMeUserDataSource, FollowMeUserDataSource followMeUserDataSource2) {
        this.localDataSource = followMeUserDataSource;
        this.remoteDataSource = followMeUserDataSource2;
    }

    private Maybe<List<FollowMeUser>> getRemotelyAndSave(long l) {
        return this.remoteDataSource.getFollowMeUsersMaybe(l).compose(RxUtil.applyMaybeSchedulers()).flatMap(FollowMeUserRepository$$Lambda$10.lambdaFactory$(this, l));
    }

    private Single<FollowMeUser> syncDelete(long l, FollowMeUser followMeUser) {
        return this.remoteDataSource.deleteFollowMeUser(l, followMeUser).compose(RxUtil.applySingleSchedulers()).flatMap(FollowMeUserRepository$$Lambda$8.lambdaFactory$(this, l, followMeUser)).onErrorResumeNext(FollowMeUserRepository$$Lambda$9.lambdaFactory$(this, l, followMeUser));
    }

    @Override
    public Single<FollowMeUser> deleteFollowMeUser(long l, FollowMeUser followMeUser) {
        return this.localDataSource.inviteOrSaveUserToFollowMe(l, followMeUser, SyncStatus.NEED_DELETE).flatMap(FollowMeUserRepository$$Lambda$3.lambdaFactory$(this, l, followMeUser));
    }

    public Flowable<List<FollowMeUser>> getFollowMeUsers(long l) {
        return this.localDataSource.getFollowMeUsersMaybe(l).mergeWith(this.getRemotelyAndSave(l).flatMap(FollowMeUserRepository$$Lambda$1.lambdaFactory$(this, l)));
    }

    @Override
    public Maybe<List<FollowMeUser>> getFollowMeUsersMaybe(long l) {
        return this.localDataSource.getFollowMeUsersMaybe(l);
    }

    @Override
    public Single<FollowMeUser> inviteOrSaveUserToFollowMe(long l, FollowMeUser followMeUser, SyncStatus syncStatus) {
        return this.localDataSource.inviteOrSaveUserToFollowMe(l, followMeUser, SyncStatus.NEED_UPLOAD).flatMap(FollowMeUserRepository$$Lambda$2.lambdaFactory$(this, l, followMeUser));
    }

    /* synthetic */ SingleSource lambda$deleteFollowMeUser$4(long l, FollowMeUser followMeUser, FollowMeUser followMeUser2) throws Exception {
        return this.syncDelete(l, followMeUser);
    }

    /* synthetic */ MaybeSource lambda$getFollowMeUsers$0(long l, List list) throws Exception {
        return this.localDataSource.getFollowMeUsersMaybe(l);
    }

    /* synthetic */ MaybeSource lambda$getRemotelyAndSave$11(long l, List list) throws Exception {
        return this.localDataSource.rewriteFollowMeUsers(l, list);
    }

    /* synthetic */ SingleSource lambda$inviteOrSaveUserToFollowMe$3(long l, FollowMeUser followMeUser, FollowMeUser followMeUser2) throws Exception {
        return this.remoteDataSource.inviteOrSaveUserToFollowMe(l, followMeUser2, null).compose(RxUtil.applySingleSchedulers()).flatMap(FollowMeUserRepository$$Lambda$11.lambdaFactory$(this, l, followMeUser)).doOnError(FollowMeUserRepository$$Lambda$12.lambdaFactory$(this, l, followMeUser2));
    }

    /* synthetic */ SingleSource lambda$null$1(long l, FollowMeUser followMeUser, FollowMeUser followMeUser2) throws Exception {
        return this.localDataSource.inviteOrSaveUserToFollowMe(l, followMeUser, SyncStatus.UP_TO_DATE);
    }

    /* synthetic */ void lambda$null$2(long l, FollowMeUser followMeUser, Throwable throwable) throws Exception {
        if (throwable instanceof ApiResponseException) {
            this.localDataSource.deleteFollowMeUser(l, followMeUser);
        }
    }

    /* synthetic */ SingleSource lambda$syncDelete$10(long l, FollowMeUser followMeUser, Throwable throwable) throws Exception {
        if (throwable instanceof ApiResponseException) {
            this.localDataSource.deleteFollowMeUser(l, followMeUser);
        }
        return Single.error(throwable);
    }

    /* synthetic */ SingleSource lambda$syncDelete$9(long l, FollowMeUser followMeUser, FollowMeUser followMeUser2) throws Exception {
        return this.localDataSource.deleteFollowMeUser(l, followMeUser);
    }

    @Override
    public Maybe<List<FollowMeUser>> rewriteFollowMeUsers(long l, List<FollowMeUser> list) {
        return this.localDataSource.rewriteFollowMeUsers(l, list);
    }
}

