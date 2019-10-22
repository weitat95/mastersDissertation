/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class IFollowUserLocalDataSource$$Lambda$3
implements Realm.Transaction {
    private final IFollowUserLocalDataSource arg$1;
    private final List arg$2;
    private final long arg$3;
    private final User arg$4;

    private IFollowUserLocalDataSource$$Lambda$3(IFollowUserLocalDataSource iFollowUserLocalDataSource, List list, long l, User user) {
        this.arg$1 = iFollowUserLocalDataSource;
        this.arg$2 = list;
        this.arg$3 = l;
        this.arg$4 = user;
    }

    public static Realm.Transaction lambdaFactory$(IFollowUserLocalDataSource iFollowUserLocalDataSource, List list, long l, User user) {
        return new IFollowUserLocalDataSource$$Lambda$3(iFollowUserLocalDataSource, list, l, user);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        this.arg$1.lambda$rewriteIFollowUsers$3(this.arg$2, this.arg$3, this.arg$4, realm);
    }
}

