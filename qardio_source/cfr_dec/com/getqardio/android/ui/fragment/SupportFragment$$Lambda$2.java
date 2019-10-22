/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.SupportFragment;
import com.getqardio.android.utils.logger.QardioLogger;
import java.lang.invoke.LambdaForm;

final class SupportFragment$$Lambda$2
implements QardioLogger.ReadCallback {
    private final SupportFragment arg$1;
    private final QardioLogger arg$2;
    private final StringBuilder arg$3;

    private SupportFragment$$Lambda$2(SupportFragment supportFragment, QardioLogger qardioLogger, StringBuilder stringBuilder) {
        this.arg$1 = supportFragment;
        this.arg$2 = qardioLogger;
        this.arg$3 = stringBuilder;
    }

    public static QardioLogger.ReadCallback lambdaFactory$(SupportFragment supportFragment, QardioLogger qardioLogger, StringBuilder stringBuilder) {
        return new SupportFragment$$Lambda$2(supportFragment, qardioLogger, stringBuilder);
    }

    @LambdaForm.Hidden
    @Override
    public void onReadCompleted(String string2) {
        this.arg$1.lambda$composeSupportTicket$1(this.arg$2, this.arg$3, string2);
    }
}

