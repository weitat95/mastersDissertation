/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.User;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class IFollowUserRemoteDataSource$$Lambda$3
implements Function {
    private final IFollowUserRemoteDataSource arg$1;
    private final long arg$2;

    private IFollowUserRemoteDataSource$$Lambda$3(IFollowUserRemoteDataSource iFollowUserRemoteDataSource, long l) {
        this.arg$1 = iFollowUserRemoteDataSource;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(IFollowUserRemoteDataSource iFollowUserRemoteDataSource, long l) {
        return new IFollowUserRemoteDataSource$$Lambda$3(iFollowUserRemoteDataSource, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$getIFollowUsersMaybe$1(this.arg$2, (User)object);
    }
}

