/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.getqardio.android.mvp.common.remote.ServerInterface;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.common.InviteDeleteAcceptToFollowMeRequest;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource$$Lambda$2;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource$$Lambda$3;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource$$Lambda$4;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource$$Lambda$5;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource$$Lambda$6;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.GetFollowingMeUserResponse;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.User;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.List;

public class FollowMeUserRemoteDataSource
implements FollowMeUserDataSource {
    private AccountContextHelper accountContextHelper;
    private final ServerInterface serverInterface;

    public FollowMeUserRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        this.serverInterface = serverInterface;
        this.accountContextHelper = accountContextHelper;
    }

    static /* synthetic */ FollowMeUser lambda$deleteFollowMeUser$4(FollowMeUser followMeUser, BaseResponse baseResponse) throws Exception {
        return followMeUser;
    }

    static /* synthetic */ Iterable lambda$getFollowMeUsersMaybe$0(List list) throws Exception {
        return list;
    }

    static /* synthetic */ boolean lambda$getFollowMeUsersMaybe$2(List list) throws Exception {
        return !list.isEmpty();
    }

    static /* synthetic */ FollowMeUser lambda$inviteOrSaveUserToFollowMe$3(FollowMeUser followMeUser, BaseResponse baseResponse) throws Exception {
        return followMeUser;
    }

    private FollowMeUser mapRespToFollowingMeUser(long l, User user, String string2) {
        FollowMeUser followMeUser = new FollowMeUser();
        followMeUser.setUserId(l);
        followMeUser.setUserEmail(string2);
        followMeUser.setAccessStatus(FFTypes.Status.valueOf(user.getAccessStatus()));
        followMeUser.setNotificationsEnabled(user.getNotifications());
        followMeUser.setWatcherEmail(user.getWatcherEmail());
        followMeUser.setFirstName(user.getFirstName());
        followMeUser.setLastName(user.getLastName());
        return followMeUser;
    }

    @Override
    public Single<FollowMeUser> deleteFollowMeUser(long l, FollowMeUser followMeUser) {
        return this.serverInterface.deleteFollowingMeUser(this.accountContextHelper.getAuthToken(l), new InviteDeleteAcceptToFollowMeRequest(followMeUser.getWatcherEmail())).map(FollowMeUserRemoteDataSource$$Lambda$6.lambdaFactory$(followMeUser));
    }

    @Override
    public Maybe<List<FollowMeUser>> getFollowMeUsersMaybe(long l) {
        com.getqardio.android.mvp.common.local.model.User user = this.accountContextHelper.getCurrentLoggedUser(l);
        if (user == null) {
            throw new RuntimeException("user not logged in");
        }
        return this.serverInterface.getFollowingMeUsers(this.accountContextHelper.getAuthToken(l)).map(FollowMeUserRemoteDataSource$$Lambda$1.lambdaFactory$()).flattenAsObservable(FollowMeUserRemoteDataSource$$Lambda$2.lambdaFactory$()).map(FollowMeUserRemoteDataSource$$Lambda$3.lambdaFactory$(this, l, user)).toList().toMaybe().filter(FollowMeUserRemoteDataSource$$Lambda$4.lambdaFactory$());
    }

    @Override
    public Single<FollowMeUser> inviteOrSaveUserToFollowMe(long l, FollowMeUser followMeUser, SyncStatus syncStatus) {
        return this.serverInterface.inviteUserToFollowMe(this.accountContextHelper.getAuthToken(l), new InviteDeleteAcceptToFollowMeRequest(followMeUser.getWatcherEmail())).map(FollowMeUserRemoteDataSource$$Lambda$5.lambdaFactory$(followMeUser));
    }

    /* synthetic */ FollowMeUser lambda$getFollowMeUsersMaybe$1(long l, com.getqardio.android.mvp.common.local.model.User user, User user2) throws Exception {
        return this.mapRespToFollowingMeUser(l, user2, user.getEmail());
    }

    @Override
    public Maybe<List<FollowMeUser>> rewriteFollowMeUsers(long l, List<FollowMeUser> list) {
        throw new UnsupportedOperationException();
    }
}

