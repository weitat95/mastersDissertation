/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.LocationFragment;
import java.lang.invoke.LambdaForm;

final class LocationFragment$$Lambda$1
implements View.OnClickListener {
    private final LocationFragment arg$1;

    private LocationFragment$$Lambda$1(LocationFragment locationFragment) {
        this.arg$1 = locationFragment;
    }

    public static View.OnClickListener lambdaFactory$(LocationFragment locationFragment) {
        return new LocationFragment$$Lambda$1(locationFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}

