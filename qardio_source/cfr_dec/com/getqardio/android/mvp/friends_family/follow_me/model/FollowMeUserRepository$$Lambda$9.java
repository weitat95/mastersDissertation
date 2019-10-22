/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model;

import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class FollowMeUserRepository$$Lambda$9
implements Function {
    private final FollowMeUserRepository arg$1;
    private final long arg$2;
    private final FollowMeUser arg$3;

    private FollowMeUserRepository$$Lambda$9(FollowMeUserRepository followMeUserRepository, long l, FollowMeUser followMeUser) {
        this.arg$1 = followMeUserRepository;
        this.arg$2 = l;
        this.arg$3 = followMeUser;
    }

    public static Function lambdaFactory$(FollowMeUserRepository followMeUserRepository, long l, FollowMeUser followMeUser) {
        return new FollowMeUserRepository$$Lambda$9(followMeUserRepository, l, followMeUser);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$syncDelete$10(this.arg$2, this.arg$3, (Throwable)object);
    }
}

