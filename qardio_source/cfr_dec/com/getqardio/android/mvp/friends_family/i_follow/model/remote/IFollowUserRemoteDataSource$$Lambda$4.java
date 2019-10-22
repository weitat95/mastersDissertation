/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource;
import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class IFollowUserRemoteDataSource$$Lambda$4
implements Predicate {
    private static final IFollowUserRemoteDataSource$$Lambda$4 instance = new IFollowUserRemoteDataSource$$Lambda$4();

    private IFollowUserRemoteDataSource$$Lambda$4() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return IFollowUserRemoteDataSource.lambda$getIFollowUsersMaybe$2((List)object);
    }
}

