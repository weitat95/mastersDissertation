/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBOnSettingsReadyFragment;
import java.lang.invoke.LambdaForm;

final class QBOnSettingsReadyFragment$$Lambda$1
implements View.OnClickListener {
    private final QBOnSettingsReadyFragment arg$1;

    private QBOnSettingsReadyFragment$$Lambda$1(QBOnSettingsReadyFragment qBOnSettingsReadyFragment) {
        this.arg$1 = qBOnSettingsReadyFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBOnSettingsReadyFragment qBOnSettingsReadyFragment) {
        return new QBOnSettingsReadyFragment$$Lambda$1(qBOnSettingsReadyFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

