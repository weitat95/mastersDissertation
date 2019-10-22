/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.User;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class FollowMeUserRemoteDataSource$$Lambda$3
implements Function {
    private final FollowMeUserRemoteDataSource arg$1;
    private final long arg$2;
    private final com.getqardio.android.mvp.common.local.model.User arg$3;

    private FollowMeUserRemoteDataSource$$Lambda$3(FollowMeUserRemoteDataSource followMeUserRemoteDataSource, long l, com.getqardio.android.mvp.common.local.model.User user) {
        this.arg$1 = followMeUserRemoteDataSource;
        this.arg$2 = l;
        this.arg$3 = user;
    }

    public static Function lambdaFactory$(FollowMeUserRemoteDataSource followMeUserRemoteDataSource, long l, com.getqardio.android.mvp.common.local.model.User user) {
        return new FollowMeUserRemoteDataSource$$Lambda$3(followMeUserRemoteDataSource, l, user);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$getFollowMeUsersMaybe$1(this.arg$2, this.arg$3, (User)object);
    }
}

