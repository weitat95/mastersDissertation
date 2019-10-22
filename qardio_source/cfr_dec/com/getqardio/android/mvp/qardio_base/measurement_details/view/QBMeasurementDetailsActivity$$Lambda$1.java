/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.view;

import android.view.View;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity;
import java.lang.invoke.LambdaForm;

final class QBMeasurementDetailsActivity$$Lambda$1
implements View.OnClickListener {
    private final QBMeasurementDetailsActivity arg$1;

    private QBMeasurementDetailsActivity$$Lambda$1(QBMeasurementDetailsActivity qBMeasurementDetailsActivity) {
        this.arg$1 = qBMeasurementDetailsActivity;
    }

    public static View.OnClickListener lambdaFactory$(QBMeasurementDetailsActivity qBMeasurementDetailsActivity) {
        return new QBMeasurementDetailsActivity$$Lambda$1(qBMeasurementDetailsActivity);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreate$0(view);
    }
}

