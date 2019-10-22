/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils;

import com.getqardio.android.utils.InvitationManager;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class InvitationManager$$Lambda$2
implements Function {
    private static final InvitationManager$$Lambda$2 instance = new InvitationManager$$Lambda$2();

    private InvitationManager$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return InvitationManager.lambda$invite$1((Throwable)object);
    }
}

