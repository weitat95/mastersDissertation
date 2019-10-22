/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.device_related_services.map.MapUiHelper;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment;
import java.lang.invoke.LambdaForm;

final class SelectLocationTagFragment$$Lambda$1
implements MapUiHelper.MapLoaded {
    private final SelectLocationTagFragment arg$1;

    private SelectLocationTagFragment$$Lambda$1(SelectLocationTagFragment selectLocationTagFragment) {
        this.arg$1 = selectLocationTagFragment;
    }

    public static MapUiHelper.MapLoaded lambdaFactory$(SelectLocationTagFragment selectLocationTagFragment) {
        return new SelectLocationTagFragment$$Lambda$1(selectLocationTagFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void mapLoaded() {
        SelectLocationTagFragment.access$lambda$0(this.arg$1);
    }
}

