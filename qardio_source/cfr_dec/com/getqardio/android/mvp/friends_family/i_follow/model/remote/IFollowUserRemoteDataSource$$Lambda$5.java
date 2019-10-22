/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class IFollowUserRemoteDataSource$$Lambda$5
implements Function {
    private final IFollowUser arg$1;

    private IFollowUserRemoteDataSource$$Lambda$5(IFollowUser iFollowUser) {
        this.arg$1 = iFollowUser;
    }

    public static Function lambdaFactory$(IFollowUser iFollowUser) {
        return new IFollowUserRemoteDataSource$$Lambda$5(iFollowUser);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return IFollowUserRemoteDataSource.lambda$deleteIFollowUser$3(this.arg$1, (BaseResponse)object);
    }
}

