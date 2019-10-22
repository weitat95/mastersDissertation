/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp;

import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.util.RxEventBus;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class MvpApplication$$Lambda$1
implements Consumer {
    private final MvpApplication arg$1;

    private MvpApplication$$Lambda$1(MvpApplication mvpApplication) {
        this.arg$1 = mvpApplication;
    }

    public static Consumer lambdaFactory$(MvpApplication mvpApplication) {
        return new MvpApplication$$Lambda$1(mvpApplication);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onCreate$0((RxEventBus.UnauthorizedResponse)object);
    }
}

