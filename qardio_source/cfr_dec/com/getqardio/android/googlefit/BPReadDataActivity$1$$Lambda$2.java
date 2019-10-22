/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class BPReadDataActivity$1$$Lambda$2
implements Consumer {
    private static final BPReadDataActivity$1$$Lambda$2 instance = new BPReadDataActivity$1$$Lambda$2();

    private BPReadDataActivity$1$$Lambda$2() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        ((Throwable)object).printStackTrace();
    }
}

