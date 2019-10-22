/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBPregnancySetupFragment;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class QBPregnancySetupFragment$$Lambda$1
implements Function {
    private final QBPregnancySetupFragment arg$1;

    private QBPregnancySetupFragment$$Lambda$1(QBPregnancySetupFragment qBPregnancySetupFragment) {
        this.arg$1 = qBPregnancySetupFragment;
    }

    public static Function lambdaFactory$(QBPregnancySetupFragment qBPregnancySetupFragment) {
        return new QBPregnancySetupFragment$$Lambda$1(qBPregnancySetupFragment);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$savePregnancyModeData$0(object);
    }
}

