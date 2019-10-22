/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.local;

import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class FollowMeUserLocalDataSource$$Lambda$3
implements Realm.Transaction {
    private final FollowMeUserLocalDataSource arg$1;
    private final List arg$2;
    private final long arg$3;
    private final User arg$4;

    private FollowMeUserLocalDataSource$$Lambda$3(FollowMeUserLocalDataSource followMeUserLocalDataSource, List list, long l, User user) {
        this.arg$1 = followMeUserLocalDataSource;
        this.arg$2 = list;
        this.arg$3 = l;
        this.arg$4 = user;
    }

    public static Realm.Transaction lambdaFactory$(FollowMeUserLocalDataSource followMeUserLocalDataSource, List list, long l, User user) {
        return new FollowMeUserLocalDataSource$$Lambda$3(followMeUserLocalDataSource, list, l, user);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        this.arg$1.lambda$rewriteFollowMeUsers$3(this.arg$2, this.arg$3, this.arg$4, realm);
    }
}

