/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.activity;

import com.getqardio.android.ui.activity.MainActivity;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class MainActivity$$Lambda$1
implements Action {
    private static final MainActivity$$Lambda$1 instance = new MainActivity$$Lambda$1();

    private MainActivity$$Lambda$1() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        MainActivity.lambda$onCreate$0();
    }
}

