/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.local;

import com.getqardio.android.mvp.common.local.model.User;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

public class AccountContextHelper {
    private Realm realm;

    public AccountContextHelper(Realm realm) {
        this.realm = realm;
    }

    public String getAuthToken(long l) {
        User user = this.getCurrentLoggedUser(l);
        if (user == null) {
            throw new RuntimeException("User not logged in");
        }
        return user.getToken();
    }

    public User getCurrentLoggedUser(long l) {
        User user = this.realm.where(User.class).equalTo("userId", l).findFirst();
        if (user != null) {
            return this.realm.copyFromRealm(user);
        }
        return null;
    }
}

