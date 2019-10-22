/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBPregnancySetupFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class QBPregnancySetupFragment$$Lambda$2
implements Consumer {
    private final QBPregnancySetupFragment arg$1;

    private QBPregnancySetupFragment$$Lambda$2(QBPregnancySetupFragment qBPregnancySetupFragment) {
        this.arg$1 = qBPregnancySetupFragment;
    }

    public static Consumer lambdaFactory$(QBPregnancySetupFragment qBPregnancySetupFragment) {
        return new QBPregnancySetupFragment$$Lambda$2(qBPregnancySetupFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$savePregnancyModeData$1(object);
    }
}

