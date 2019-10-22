/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.device_related_services.map.MapUiHelper;
import com.getqardio.android.device_related_services.map.QPoint;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment;
import java.lang.invoke.LambdaForm;

final class SelectLocationTagFragment$$Lambda$2
implements MapUiHelper.LocationConverted {
    private final SelectLocationTagFragment arg$1;

    private SelectLocationTagFragment$$Lambda$2(SelectLocationTagFragment selectLocationTagFragment) {
        this.arg$1 = selectLocationTagFragment;
    }

    public static MapUiHelper.LocationConverted lambdaFactory$(SelectLocationTagFragment selectLocationTagFragment) {
        return new SelectLocationTagFragment$$Lambda$2(selectLocationTagFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void locationConverted(QPoint qPoint) {
        this.arg$1.lambda$setInitPositionOfInfoWindow$1(qPoint);
    }
}

