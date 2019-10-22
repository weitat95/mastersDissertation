/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm;

import com.getqardio.android.fcm.QardioFirebaseInstanceIDService;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class QardioFirebaseInstanceIDService$$Lambda$2
implements Consumer {
    private final QardioFirebaseInstanceIDService arg$1;

    private QardioFirebaseInstanceIDService$$Lambda$2(QardioFirebaseInstanceIDService qardioFirebaseInstanceIDService) {
        this.arg$1 = qardioFirebaseInstanceIDService;
    }

    public static Consumer lambdaFactory$(QardioFirebaseInstanceIDService qardioFirebaseInstanceIDService) {
        return new QardioFirebaseInstanceIDService$$Lambda$2(qardioFirebaseInstanceIDService);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$sendRegistrationToServer$1((Throwable)object);
    }
}

