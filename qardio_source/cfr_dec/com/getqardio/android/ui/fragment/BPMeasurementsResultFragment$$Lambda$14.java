/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.device_related_services.map.QLatLng;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import io.reactivex.ObservableSource;
import java.lang.invoke.LambdaForm;
import java.util.concurrent.Callable;

final class BPMeasurementsResultFragment$$Lambda$14
implements Callable {
    private final BPMeasurementsResultFragment arg$1;
    private final QLatLng arg$2;

    private BPMeasurementsResultFragment$$Lambda$14(BPMeasurementsResultFragment bPMeasurementsResultFragment, QLatLng qLatLng) {
        this.arg$1 = bPMeasurementsResultFragment;
        this.arg$2 = qLatLng;
    }

    public static Callable lambdaFactory$(BPMeasurementsResultFragment bPMeasurementsResultFragment, QLatLng qLatLng) {
        return new BPMeasurementsResultFragment$$Lambda$14(bPMeasurementsResultFragment, qLatLng);
    }

    @LambdaForm.Hidden
    public Object call() {
        return this.arg$1.lambda$detectLocationTag$9(this.arg$2);
    }
}

