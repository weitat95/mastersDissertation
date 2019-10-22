/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.widget.DatePicker
 */
package com.getqardio.android.ui.fragment;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment;
import java.lang.invoke.LambdaForm;

final class QBOnboardingConfirmProfileFragment$$Lambda$5
implements DatePickerDialog.OnDateSetListener {
    private final QBOnboardingConfirmProfileFragment arg$1;

    private QBOnboardingConfirmProfileFragment$$Lambda$5(QBOnboardingConfirmProfileFragment qBOnboardingConfirmProfileFragment) {
        this.arg$1 = qBOnboardingConfirmProfileFragment;
    }

    public static DatePickerDialog.OnDateSetListener lambdaFactory$(QBOnboardingConfirmProfileFragment qBOnboardingConfirmProfileFragment) {
        return new QBOnboardingConfirmProfileFragment$$Lambda$5(qBOnboardingConfirmProfileFragment);
    }

    @LambdaForm.Hidden
    public void onDateSet(DatePicker datePicker, int n, int n2, int n3) {
        this.arg$1.lambda$selectDOB$4(datePicker, n, n2, n3);
    }
}

