/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource;
import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class FollowMeUserRemoteDataSource$$Lambda$4
implements Predicate {
    private static final FollowMeUserRemoteDataSource$$Lambda$4 instance = new FollowMeUserRemoteDataSource$$Lambda$4();

    private FollowMeUserRemoteDataSource$$Lambda$4() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return FollowMeUserRemoteDataSource.lambda$getFollowMeUsersMaybe$2((List)object);
    }
}

