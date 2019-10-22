/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model;

import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class FollowMeUserRepository$$Lambda$12
implements Consumer {
    private final FollowMeUserRepository arg$1;
    private final long arg$2;
    private final FollowMeUser arg$3;

    private FollowMeUserRepository$$Lambda$12(FollowMeUserRepository followMeUserRepository, long l, FollowMeUser followMeUser) {
        this.arg$1 = followMeUserRepository;
        this.arg$2 = l;
        this.arg$3 = followMeUser;
    }

    public static Consumer lambdaFactory$(FollowMeUserRepository followMeUserRepository, long l, FollowMeUser followMeUser) {
        return new FollowMeUserRepository$$Lambda$12(followMeUserRepository, l, followMeUser);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$null$2(this.arg$2, this.arg$3, (Throwable)object);
    }
}

