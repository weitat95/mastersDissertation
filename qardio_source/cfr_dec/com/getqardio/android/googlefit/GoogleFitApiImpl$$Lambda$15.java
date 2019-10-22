/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class GoogleFitApiImpl$$Lambda$15
implements Function {
    private final GoogleFitApiImpl arg$1;
    private final BPMeasurement arg$2;

    private GoogleFitApiImpl$$Lambda$15(GoogleFitApiImpl googleFitApiImpl, BPMeasurement bPMeasurement) {
        this.arg$1 = googleFitApiImpl;
        this.arg$2 = bPMeasurement;
    }

    public static Function lambdaFactory$(GoogleFitApiImpl googleFitApiImpl, BPMeasurement bPMeasurement) {
        return new GoogleFitApiImpl$$Lambda$15(googleFitApiImpl, bPMeasurement);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$saveBloodPressureMeasurement$17(this.arg$2, (Boolean)object);
    }
}

