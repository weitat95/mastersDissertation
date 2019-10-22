/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.view;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity;
import com.getqardio.android.ui.widget.BmiResultChart;

public class QBMeasurementDetailsActivity_ViewBinding
implements Unbinder {
    private QBMeasurementDetailsActivity target;

    public QBMeasurementDetailsActivity_ViewBinding(QBMeasurementDetailsActivity qBMeasurementDetailsActivity, View view) {
        this.target = qBMeasurementDetailsActivity;
        qBMeasurementDetailsActivity.fatView = Utils.findRequiredViewAsType(view, 2131820871, "field 'fatView'", TextView.class);
        qBMeasurementDetailsActivity.muscleView = Utils.findRequiredViewAsType(view, 2131820872, "field 'muscleView'", TextView.class);
        qBMeasurementDetailsActivity.waterView = Utils.findRequiredViewAsType(view, 2131820873, "field 'waterView'", TextView.class);
        qBMeasurementDetailsActivity.boneView = Utils.findRequiredViewAsType(view, 2131820874, "field 'boneView'", TextView.class);
        qBMeasurementDetailsActivity.timeView = Utils.findRequiredViewAsType(view, 2131821415, "field 'timeView'", TextView.class);
        qBMeasurementDetailsActivity.dateView = Utils.findRequiredViewAsType(view, 2131821414, "field 'dateView'", TextView.class);
        qBMeasurementDetailsActivity.bmiChart = Utils.findRequiredViewAsType(view, 2131820875, "field 'bmiChart'", BmiResultChart.class);
        qBMeasurementDetailsActivity.weightView = Utils.findRequiredViewAsType(view, 2131820869, "field 'weightView'", TextView.class);
        qBMeasurementDetailsActivity.weightUnitView = Utils.findRequiredViewAsType(view, 2131820870, "field 'weightUnitView'", TextView.class);
        qBMeasurementDetailsActivity.noteView = Utils.findRequiredViewAsType(view, 2131820923, "field 'noteView'", TextView.class);
    }
}

