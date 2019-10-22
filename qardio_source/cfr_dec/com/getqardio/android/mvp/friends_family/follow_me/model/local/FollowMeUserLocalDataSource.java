/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.local;

import android.text.TextUtils;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource$$Lambda$2;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource$$Lambda$3;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource$$Lambda$4;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class FollowMeUserLocalDataSource
implements FollowMeUserDataSource {
    private AccountContextHelper accountContextHelper;
    private Realm realm;

    public FollowMeUserLocalDataSource(Realm realm, AccountContextHelper accountContextHelper) {
        this.realm = realm;
        this.accountContextHelper = accountContextHelper;
    }

    @Override
    public Single<FollowMeUser> deleteFollowMeUser(long l, FollowMeUser followMeUser) {
        this.realm.executeTransaction(FollowMeUserLocalDataSource$$Lambda$2.lambdaFactory$(this, l, followMeUser));
        return Single.just(followMeUser);
    }

    @Override
    public Maybe<List<FollowMeUser>> getFollowMeUsersMaybe(long l) {
        Object object = this.accountContextHelper.getCurrentLoggedUser(l);
        if (object == null) {
            throw new RuntimeException("user not logged it");
        }
        object = this.realm.where(FollowMeUser.class).equalTo("userId", l).notEqualTo("syncStatus", SyncStatus.NEED_DELETE.ordinal()).equalTo("userEmail", ((User)object).getEmail()).findAll();
        Timber.d("users - %s", TextUtils.join((CharSequence)", ", (Iterable)object));
        if (((AbstractCollection)object).isEmpty()) {
            return Maybe.empty();
        }
        return Maybe.just(this.realm.copyFromRealm(object));
    }

    @Override
    public Single<FollowMeUser> inviteOrSaveUserToFollowMe(long l, FollowMeUser followMeUser, SyncStatus syncStatus) {
        User user = this.accountContextHelper.getCurrentLoggedUser(l);
        if (user == null) {
            throw new RuntimeException("user not logged it ");
        }
        Timber.d("inviteOrSaveUserToFollowMe email - %s, status - %s", new Object[]{followMeUser.getWatcherEmail(), syncStatus});
        return Single.create(FollowMeUserLocalDataSource$$Lambda$1.lambdaFactory$(this, followMeUser, user, syncStatus));
    }

    /* synthetic */ void lambda$deleteFollowMeUser$2(long l, FollowMeUser followMeUser, Realm realm) {
        followMeUser = this.realm.where(FollowMeUser.class).equalTo("userId", l).equalTo("watcherEmail", followMeUser.getWatcherEmail()).findFirst();
        if (followMeUser != null) {
            RealmObject.deleteFromRealm(followMeUser);
        }
    }

    /* synthetic */ void lambda$inviteOrSaveUserToFollowMe$1(FollowMeUser followMeUser, User user, SyncStatus syncStatus, SingleEmitter singleEmitter) throws Exception {
        this.realm.executeTransaction(FollowMeUserLocalDataSource$$Lambda$4.lambdaFactory$(this, followMeUser, user, syncStatus, singleEmitter));
    }

    /* synthetic */ void lambda$null$0(FollowMeUser followMeUser, User user, SyncStatus syncStatus, SingleEmitter singleEmitter, Realm realm) {
        followMeUser.setUserEmail(user.getEmail());
        followMeUser.setSyncStatus(syncStatus);
        singleEmitter.onSuccess(this.realm.copyToRealmOrUpdate(followMeUser));
    }

    /* synthetic */ void lambda$rewriteFollowMeUsers$3(List object, long l, User user, Realm object2) {
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (FollowMeUser)object.next();
            FollowMeUser followMeUser = this.realm.where(FollowMeUser.class).equalTo("userId", l).equalTo("userEmail", user.getEmail()).equalTo("watcherEmail", ((FollowMeUser)object2).getWatcherEmail()).findFirst();
            if (followMeUser != null && followMeUser.getSyncStatus() != SyncStatus.UP_TO_DATE) continue;
            ((FollowMeUser)object2).setSyncStatus(SyncStatus.UP_TO_DATE);
            this.realm.copyToRealmOrUpdate(object2);
        }
    }

    @Override
    public Maybe<List<FollowMeUser>> rewriteFollowMeUsers(long l, List<FollowMeUser> list) {
        User user = this.accountContextHelper.getCurrentLoggedUser(l);
        if (user == null) {
            throw new RuntimeException("user not logged it");
        }
        this.realm.executeTransaction(FollowMeUserLocalDataSource$$Lambda$3.lambdaFactory$(this, list, l, user));
        return Maybe.just(list);
    }
}

