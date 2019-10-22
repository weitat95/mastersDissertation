/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class SelectLocationTagFragment$$Lambda$3
implements Function {
    private final BPMeasurement arg$1;
    private final long arg$2;

    private SelectLocationTagFragment$$Lambda$3(BPMeasurement bPMeasurement, long l) {
        this.arg$1 = bPMeasurement;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(BPMeasurement bPMeasurement, long l) {
        return new SelectLocationTagFragment$$Lambda$3(bPMeasurement, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return SelectLocationTagFragment.lambda$applyTag$2(this.arg$1, this.arg$2, object);
    }
}

