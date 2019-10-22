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

final class QBOnboardingActivity$$Lambda$2
implements DialogInterface.OnClickListener {
    private static final QBOnboardingActivity$$Lambda$2 instance = new QBOnboardingActivity$$Lambda$2();

    private QBOnboardingActivity$$Lambda$2() {
    }

    public static DialogInterface.OnClickListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        QBOnboardingActivity.lambda$onBackPressed$1(dialogInterface, n);
    }
}

