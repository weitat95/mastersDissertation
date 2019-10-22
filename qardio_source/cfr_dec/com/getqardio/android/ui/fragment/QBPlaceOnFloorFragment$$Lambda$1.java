/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBPlaceOnFloorFragment;
import java.lang.invoke.LambdaForm;

final class QBPlaceOnFloorFragment$$Lambda$1
implements View.OnClickListener {
    private final QBPlaceOnFloorFragment arg$1;

    private QBPlaceOnFloorFragment$$Lambda$1(QBPlaceOnFloorFragment qBPlaceOnFloorFragment) {
        this.arg$1 = qBPlaceOnFloorFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBPlaceOnFloorFragment qBPlaceOnFloorFragment) {
        return new QBPlaceOnFloorFragment$$Lambda$1(qBPlaceOnFloorFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

