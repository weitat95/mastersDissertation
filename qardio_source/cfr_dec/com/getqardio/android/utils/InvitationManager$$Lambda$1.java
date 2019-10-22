/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils;

import com.getqardio.android.io.network.invite.models.InviteResponse;
import com.getqardio.android.utils.InvitationManager;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class InvitationManager$$Lambda$1
implements Function {
    private static final InvitationManager$$Lambda$1 instance = new InvitationManager$$Lambda$1();

    private InvitationManager$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return InvitationManager.lambda$invite$0((InviteResponse)object);
    }
}

