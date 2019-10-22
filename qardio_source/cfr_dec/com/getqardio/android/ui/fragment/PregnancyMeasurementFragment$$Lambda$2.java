/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.content.DialogInterface;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class PregnancyMeasurementFragment$$Lambda$2
implements DialogInterface.OnClickListener {
    private final PregnancyMeasurementFragment arg$1;
    private final int arg$2;

    private PregnancyMeasurementFragment$$Lambda$2(PregnancyMeasurementFragment pregnancyMeasurementFragment, int n) {
        this.arg$1 = pregnancyMeasurementFragment;
        this.arg$2 = n;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(PregnancyMeasurementFragment pregnancyMeasurementFragment, int n) {
        return new PregnancyMeasurementFragment$$Lambda$2(pregnancyMeasurementFragment, n);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$showDeleteConfirmationDialog$1(this.arg$2, dialogInterface, n);
    }
}

