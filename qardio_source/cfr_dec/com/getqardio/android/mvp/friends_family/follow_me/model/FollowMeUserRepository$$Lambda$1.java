/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model;

import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import io.reactivex.MaybeSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class FollowMeUserRepository$$Lambda$1
implements Function {
    private final FollowMeUserRepository arg$1;
    private final long arg$2;

    private FollowMeUserRepository$$Lambda$1(FollowMeUserRepository followMeUserRepository, long l) {
        this.arg$1 = followMeUserRepository;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(FollowMeUserRepository followMeUserRepository, long l) {
        return new FollowMeUserRepository$$Lambda$1(followMeUserRepository, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$getFollowMeUsers$0(this.arg$2, (List)object);
    }
}

