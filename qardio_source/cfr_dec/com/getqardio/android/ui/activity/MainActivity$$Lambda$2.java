/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.activity;

import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.ui.activity.MainActivity;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class MainActivity$$Lambda$2
implements Consumer {
    private final FCMManager arg$1;

    private MainActivity$$Lambda$2(FCMManager fCMManager) {
        this.arg$1 = fCMManager;
    }

    public static Consumer lambdaFactory$(FCMManager fCMManager) {
        return new MainActivity$$Lambda$2(fCMManager);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        MainActivity.lambda$onCreate$1(this.arg$1, (Throwable)object);
    }
}

