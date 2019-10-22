/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.activity;

import android.content.DialogInterface;
import com.getqardio.android.ui.activity.QBOnboardingActivity;
import java.lang.invoke.LambdaForm;

final class QBOnboardingActivity$$Lambda$1
implements DialogInterface.OnClickListener {
    private final QBOnboardingActivity arg$1;

    private QBOnboardingActivity$$Lambda$1(QBOnboardingActivity qBOnboardingActivity) {
        this.arg$1 = qBOnboardingActivity;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(QBOnboardingActivity qBOnboardingActivity) {
        return new QBOnboardingActivity$$Lambda$1(qBOnboardingActivity);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$onBackPressed$0(dialogInterface, n);
    }
}

