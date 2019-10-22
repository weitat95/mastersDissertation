/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.getqardio.android.ui.fragment;

import android.os.Bundle;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingStateFragment;

public class QBModeStepOnStateFragment
extends AbstractQBOnboardingStateFragment {
    public static QBModeStepOnStateFragment newInstance(int n, int n2, int n3, boolean bl) {
        QBModeStepOnStateFragment qBModeStepOnStateFragment = new QBModeStepOnStateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("com.getqardio.android.argument.TITLE_RES_ID", n);
        bundle.putInt("com.getqardio.android.argument.MESSAGE_RES_ID", n2);
        bundle.putInt("com.getqardio.android.argument.IMAGE_RES_ID", n3);
        bundle.putBoolean("com.getqardio.android.argument.SHOW_PROGRESS", bl);
        qBModeStepOnStateFragment.setArguments(bundle);
        return qBModeStepOnStateFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837945;
    }
}

