/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm;

import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.fcm.api.PushRegistrationResponse;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class FCMManager$$Lambda$1
implements Function {
    private final FCMManager arg$1;

    private FCMManager$$Lambda$1(FCMManager fCMManager) {
        this.arg$1 = fCMManager;
    }

    public static Function lambdaFactory$(FCMManager fCMManager) {
        return new FCMManager$$Lambda$1(fCMManager);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$registerFCMToken$0((PushRegistrationResponse)object);
    }
}

