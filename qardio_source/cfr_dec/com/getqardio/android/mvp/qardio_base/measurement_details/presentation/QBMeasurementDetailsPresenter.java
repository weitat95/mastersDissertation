/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.presentation;

import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsContract;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsRepository;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter$$Lambda$1;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter$$Lambda$2;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter$$Lambda$3;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter$$Lambda$4;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Date;

public class QBMeasurementDetailsPresenter {
    private final QBMeasurementDetailsRepository repository;
    private final CompositeDisposable subscriptions = new CompositeDisposable();
    private final QBMeasurementDetailsContract.View view;

    QBMeasurementDetailsPresenter(QBMeasurementDetailsRepository qBMeasurementDetailsRepository, QBMeasurementDetailsContract.View view) {
        this.repository = qBMeasurementDetailsRepository;
        this.view = view;
    }

    static /* synthetic */ void access$lambda$0(QBMeasurementDetailsPresenter qBMeasurementDetailsPresenter, WeightMeasurement weightMeasurement) {
        qBMeasurementDetailsPresenter.setMeasurementData(weightMeasurement);
    }

    private String bodyCompositionValueToString(Float f) {
        if (f == null) {
            return "--";
        }
        return Convert.floatToString(f, 0);
    }

    static /* synthetic */ void lambda$onNoteChanged$0(String string2) throws Exception {
    }

    private boolean measurementIsValidPercentage(int n) {
        return n > 0 && n < 100;
    }

    private void setDate(WeightMeasurement object) {
        object = ((WeightMeasurement)object).measureDate;
        this.view.setTimeValue(DateUtils.getCurrentTimeFormat().format((Date)object));
        this.view.setDateValue((Date)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setMeasurementData(WeightMeasurement weightMeasurement) {
        String string2;
        this.setDate(weightMeasurement);
        int n = this.view.getWeightUnit();
        float f = MetricUtils.convertWeight(0, n, weightMeasurement.weight.floatValue());
        this.view.setWeightValue(Convert.floatToString(Float.valueOf(f), 1));
        this.view.setWeightUnitValue(MetricUtils.getWeightUnitNameRes(n));
        if (QardioBaseUtils.canCalculateBmi(weightMeasurement)) {
            this.view.setBMIChartValue(Float.valueOf(QardioBaseUtils.bmi(weightMeasurement.weight.floatValue(), weightMeasurement.height)));
        }
        if (weightMeasurement.fat != null && this.measurementIsValidPercentage(weightMeasurement.fat)) {
            this.view.setFatValue(Utils.formatInteger(weightMeasurement.fat));
        }
        if (weightMeasurement.muscle != null && this.measurementIsValidPercentage(weightMeasurement.muscle)) {
            this.view.setMuscleValue(Utils.formatInteger(weightMeasurement.muscle));
        } else if (weightMeasurement.z != null && weightMeasurement.z > 0 && weightMeasurement.measurementSource != 4) {
            string2 = this.bodyCompositionValueToString(QardioBaseUtils.musclePercentage(weightMeasurement));
            this.view.setMuscleValue(string2);
        }
        if (weightMeasurement.water != null && this.measurementIsValidPercentage(weightMeasurement.water)) {
            this.view.setWaterValue(Utils.formatInteger(weightMeasurement.water));
        } else if (weightMeasurement.z != null && weightMeasurement.z > 0 && weightMeasurement.measurementSource != 4) {
            string2 = this.bodyCompositionValueToString(QardioBaseUtils.waterPercentage(weightMeasurement));
            this.view.setWaterValue(string2);
        }
        if (weightMeasurement.bone != null && this.measurementIsValidPercentage(weightMeasurement.bone)) {
            this.view.setBoneValue(Utils.formatInteger(weightMeasurement.bone));
        } else if (weightMeasurement.z != null && weightMeasurement.z > 0 && weightMeasurement.measurementSource != 4) {
            string2 = this.bodyCompositionValueToString(QardioBaseUtils.bonePercentage(weightMeasurement));
            this.view.setBoneValue(string2);
        }
        this.view.setNoteValue(weightMeasurement.note);
    }

    public void fetchData(long l, long l2) {
        this.subscriptions.add(this.repository.getMeasurementByDate(l, l2).subscribe(QBMeasurementDetailsPresenter$$Lambda$1.lambdaFactory$(this), QBMeasurementDetailsPresenter$$Lambda$2.lambdaFactory$()));
    }

    public void onNoteChanged(long l, long l2, String string2) {
        this.subscriptions.add(this.repository.saveNewNote(l, l2, string2).subscribe(QBMeasurementDetailsPresenter$$Lambda$3.lambdaFactory$(), QBMeasurementDetailsPresenter$$Lambda$4.lambdaFactory$()));
    }

    public void subscribe() {
    }

    public void unsubscribe() {
        RxUtil.unsubscribe(this.subscriptions);
    }
}

