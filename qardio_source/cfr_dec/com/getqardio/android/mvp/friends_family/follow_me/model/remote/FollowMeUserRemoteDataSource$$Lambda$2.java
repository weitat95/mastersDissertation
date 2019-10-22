/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class FollowMeUserRemoteDataSource$$Lambda$2
implements Function {
    private static final FollowMeUserRemoteDataSource$$Lambda$2 instance = new FollowMeUserRemoteDataSource$$Lambda$2();

    private FollowMeUserRemoteDataSource$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return FollowMeUserRemoteDataSource.lambda$getFollowMeUsersMaybe$0((List)object);
    }
}

