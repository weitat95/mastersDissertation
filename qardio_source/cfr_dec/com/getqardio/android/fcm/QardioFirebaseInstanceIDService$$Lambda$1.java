/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm;

import com.getqardio.android.fcm.QardioFirebaseInstanceIDService;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class QardioFirebaseInstanceIDService$$Lambda$1
implements Action {
    private static final QardioFirebaseInstanceIDService$$Lambda$1 instance = new QardioFirebaseInstanceIDService$$Lambda$1();

    private QardioFirebaseInstanceIDService$$Lambda$1() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        QardioFirebaseInstanceIDService.lambda$sendRegistrationToServer$0();
    }
}

