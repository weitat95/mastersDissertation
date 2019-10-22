/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model;

import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class IFollowUserRepository$$Lambda$2
implements Function {
    private final IFollowUserRepository arg$1;
    private final long arg$2;
    private final IFollowUser arg$3;

    private IFollowUserRepository$$Lambda$2(IFollowUserRepository iFollowUserRepository, long l, IFollowUser iFollowUser) {
        this.arg$1 = iFollowUserRepository;
        this.arg$2 = l;
        this.arg$3 = iFollowUser;
    }

    public static Function lambdaFactory$(IFollowUserRepository iFollowUserRepository, long l, IFollowUser iFollowUser) {
        return new IFollowUserRepository$$Lambda$2(iFollowUserRepository, l, iFollowUser);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$deleteIFollowUser$1(this.arg$2, this.arg$3, (IFollowUser)object);
    }
}

