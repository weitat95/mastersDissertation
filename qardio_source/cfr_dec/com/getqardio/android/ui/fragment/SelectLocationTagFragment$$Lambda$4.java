/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.SelectLocationTagFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class SelectLocationTagFragment$$Lambda$4
implements Consumer {
    private static final SelectLocationTagFragment$$Lambda$4 instance = new SelectLocationTagFragment$$Lambda$4();

    private SelectLocationTagFragment$$Lambda$4() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        SelectLocationTagFragment.lambda$applyTag$3(object);
    }
}

