/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model;

import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import io.reactivex.MaybeSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class IFollowUserRepository$$Lambda$1
implements Function {
    private final IFollowUserRepository arg$1;
    private final long arg$2;

    private IFollowUserRepository$$Lambda$1(IFollowUserRepository iFollowUserRepository, long l) {
        this.arg$1 = iFollowUserRepository;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(IFollowUserRepository iFollowUserRepository, long l) {
        return new IFollowUserRepository$$Lambda$1(iFollowUserRepository, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$getIFollowUsers$0(this.arg$2, (List)object);
    }
}

