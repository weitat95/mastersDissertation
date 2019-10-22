/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import android.text.TextUtils;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource$$Lambda$1;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource$$Lambda$2;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource$$Lambda$3;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource$$Lambda$4;
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

public class IFollowUserLocalDataSource
implements IFollowUserDataSource {
    private AccountContextHelper accountContextHelper;
    private Realm realm;

    public IFollowUserLocalDataSource(Realm realm, AccountContextHelper accountContextHelper) {
        this.realm = realm;
        this.accountContextHelper = accountContextHelper;
    }

    static /* synthetic */ void lambda$deleteIFollowUser$2(IFollowUser iFollowUser, Realm realm) {
        RealmObject.deleteFromRealm(iFollowUser);
    }

    @Override
    public Single<IFollowUser> deleteIFollowUser(long l, IFollowUser iFollowUser) {
        this.realm.executeTransaction(IFollowUserLocalDataSource$$Lambda$2.lambdaFactory$(iFollowUser));
        return Single.just(iFollowUser);
    }

    @Override
    public Single<IFollowUser> enablePushNotifications(long l, IFollowUser iFollowUser, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<List<IFollowUser>> getIFollowUsersMaybe(long l) {
        Object object = this.accountContextHelper.getCurrentLoggedUser(l);
        if (object == null) {
            return Maybe.error(new RuntimeException("user not logged it"));
        }
        object = this.realm.where(IFollowUser.class).equalTo("userId", l).notEqualTo("syncStatus", SyncStatus.NEED_DELETE.ordinal()).equalTo("userEmail", ((User)object).getEmail()).findAll();
        Timber.d("users - %s", TextUtils.join((CharSequence)", ", (Iterable)object));
        if (((AbstractCollection)object).isEmpty()) {
            return Maybe.empty();
        }
        return Maybe.just(this.realm.copyFromRealm(object));
    }

    /* synthetic */ void lambda$null$0(IFollowUser iFollowUser, SyncStatus syncStatus, SingleEmitter singleEmitter, Realm realm) {
        iFollowUser.setSyncStatus(syncStatus);
        singleEmitter.onSuccess(this.realm.copyToRealmOrUpdate(iFollowUser));
    }

    /* synthetic */ void lambda$rewriteIFollowUsers$3(List object, long l, User user, Realm object2) {
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (IFollowUser)object.next();
            IFollowUser iFollowUser = this.realm.where(IFollowUser.class).equalTo("userId", l).equalTo("userEmail", user.getEmail()).equalTo("watchingEmail", ((IFollowUser)object2).getWatchingEmail()).findFirst();
            if (iFollowUser != null && iFollowUser.getSyncStatus() != SyncStatus.UP_TO_DATE) continue;
            ((IFollowUser)object2).setSyncStatus(SyncStatus.UP_TO_DATE);
            this.realm.copyToRealmOrUpdate(object2);
        }
    }

    /* synthetic */ void lambda$saveIFollowUser$1(IFollowUser iFollowUser, SyncStatus syncStatus, SingleEmitter singleEmitter) throws Exception {
        this.realm.executeTransaction(IFollowUserLocalDataSource$$Lambda$4.lambdaFactory$(this, iFollowUser, syncStatus, singleEmitter));
    }

    @Override
    public Maybe<List<IFollowUser>> rewriteIFollowUsers(long l, List<IFollowUser> list) {
        User user = this.accountContextHelper.getCurrentLoggedUser(l);
        if (user == null) {
            return Maybe.error(new RuntimeException("user not logged it"));
        }
        this.realm.executeTransaction(IFollowUserLocalDataSource$$Lambda$3.lambdaFactory$(this, list, l, user));
        return Maybe.just(list);
    }

    @Override
    public Single<IFollowUser> saveIFollowUser(long l, IFollowUser iFollowUser, SyncStatus syncStatus) {
        return Single.create(IFollowUserLocalDataSource$$Lambda$1.lambdaFactory$(this, iFollowUser, syncStatus));
    }
}

