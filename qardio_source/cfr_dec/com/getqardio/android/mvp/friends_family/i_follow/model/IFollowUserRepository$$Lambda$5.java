/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model;

import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class IFollowUserRepository$$Lambda$5
implements Function {
    private final IFollowUserRepository arg$1;
    private final IFollowUser arg$2;
    private final boolean arg$3;
    private final long arg$4;

    private IFollowUserRepository$$Lambda$5(IFollowUserRepository iFollowUserRepository, IFollowUser iFollowUser, boolean bl, long l) {
        this.arg$1 = iFollowUserRepository;
        this.arg$2 = iFollowUser;
        this.arg$3 = bl;
        this.arg$4 = l;
    }

    public static Function lambdaFactory$(IFollowUserRepository iFollowUserRepository, IFollowUser iFollowUser, boolean bl, long l) {
        return new IFollowUserRepository$$Lambda$5(iFollowUserRepository, iFollowUser, bl, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$enablePushNotifications$4(this.arg$2, this.arg$3, this.arg$4, (IFollowUser)object);
    }
}

