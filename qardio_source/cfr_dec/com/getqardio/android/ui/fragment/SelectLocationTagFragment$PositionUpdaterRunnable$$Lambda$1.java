/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.device_related_services.map.MapUiHelper;
import com.getqardio.android.device_related_services.map.QPoint;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment;
import java.lang.invoke.LambdaForm;

final class SelectLocationTagFragment$PositionUpdaterRunnable$$Lambda$1
implements MapUiHelper.LocationConverted {
    private final SelectLocationTagFragment.PositionUpdaterRunnable arg$1;

    private SelectLocationTagFragment$PositionUpdaterRunnable$$Lambda$1(SelectLocationTagFragment.PositionUpdaterRunnable positionUpdaterRunnable) {
        this.arg$1 = positionUpdaterRunnable;
    }

    public static MapUiHelper.LocationConverted lambdaFactory$(SelectLocationTagFragment.PositionUpdaterRunnable positionUpdaterRunnable) {
        return new SelectLocationTagFragment$PositionUpdaterRunnable$$Lambda$1(positionUpdaterRunnable);
    }

    @LambdaForm.Hidden
    @Override
    public void locationConverted(QPoint qPoint) {
        this.arg$1.lambda$run$0(qPoint);
    }
}

