/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm;

import com.getqardio.android.fcm.FCMManager;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class FCMManager$$Lambda$2
implements Consumer {
    private final FCMManager arg$1;

    private FCMManager$$Lambda$2(FCMManager fCMManager) {
        this.arg$1 = fCMManager;
    }

    public static Consumer lambdaFactory$(FCMManager fCMManager) {
        return new FCMManager$$Lambda$2(fCMManager);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$registerFCMToken$1((Throwable)object);
    }
}

