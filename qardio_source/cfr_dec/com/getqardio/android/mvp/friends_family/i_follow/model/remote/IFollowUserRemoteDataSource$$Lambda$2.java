/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class IFollowUserRemoteDataSource$$Lambda$2
implements Function {
    private static final IFollowUserRemoteDataSource$$Lambda$2 instance = new IFollowUserRemoteDataSource$$Lambda$2();

    private IFollowUserRemoteDataSource$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return IFollowUserRemoteDataSource.lambda$getIFollowUsersMaybe$0((List)object);
    }
}

