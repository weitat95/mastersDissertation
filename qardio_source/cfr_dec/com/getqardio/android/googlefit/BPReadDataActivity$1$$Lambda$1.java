/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.BPReadDataActivity;
import com.google.android.gms.fitness.result.DataReadResult;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class BPReadDataActivity$1$$Lambda$1
implements Consumer {
    private final BPReadDataActivity.1 arg$1;

    private BPReadDataActivity$1$$Lambda$1(BPReadDataActivity.1 var1_1) {
        this.arg$1 = var1_1;
    }

    public static Consumer lambdaFactory$(BPReadDataActivity.1 var0) {
        return new BPReadDataActivity$1$$Lambda$1(var0);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onConnected$0((DataReadResult)object);
    }
}

