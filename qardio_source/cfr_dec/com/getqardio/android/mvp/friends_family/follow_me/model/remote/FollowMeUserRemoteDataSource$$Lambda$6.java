/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model.remote;

import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.model.remote.FollowMeUserRemoteDataSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class FollowMeUserRemoteDataSource$$Lambda$6
implements Function {
    private final FollowMeUser arg$1;

    private FollowMeUserRemoteDataSource$$Lambda$6(FollowMeUser followMeUser) {
        this.arg$1 = followMeUser;
    }

    public static Function lambdaFactory$(FollowMeUser followMeUser) {
        return new FollowMeUserRemoteDataSource$$Lambda$6(followMeUser);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return FollowMeUserRemoteDataSource.lambda$deleteFollowMeUser$4(this.arg$1, (BaseResponse)object);
    }
}

