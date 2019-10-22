/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.friends_family.i_follow.model.remote.GetIFollowUserResponse;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.User;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class IFollowUserRemoteDataSource$$Lambda$1
implements Function {
    private static final IFollowUserRemoteDataSource$$Lambda$1 instance = new IFollowUserRemoteDataSource$$Lambda$1();

    private IFollowUserRemoteDataSource$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((GetIFollowUserResponse)object).getUsers();
    }
}

