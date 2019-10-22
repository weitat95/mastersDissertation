/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.getqardio.android.mvp.friends_family.follow_me.model.remote.GetFollowingMeUserResponse;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.User;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class FollowMeUserRemoteDataSource$$Lambda$1
implements Function {
    private static final FollowMeUserRemoteDataSource$$Lambda$1 instance = new FollowMeUserRemoteDataSource$$Lambda$1();

    private FollowMeUserRemoteDataSource$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((GetFollowingMeUserResponse)object).getUsers();
    }
}

