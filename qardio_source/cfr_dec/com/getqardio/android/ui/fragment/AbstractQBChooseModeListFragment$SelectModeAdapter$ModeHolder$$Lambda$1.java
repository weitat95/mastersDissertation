/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment;
import java.lang.invoke.LambdaForm;

final class AbstractQBChooseModeListFragment$SelectModeAdapter$ModeHolder$$Lambda$1
implements View.OnClickListener {
    private final AbstractQBChooseModeListFragment.SelectModeAdapter.ModeHolder arg$1;
    private final AbstractQBChooseModeListFragment.OperationModel arg$2;
    private final int arg$3;

    private AbstractQBChooseModeListFragment$SelectModeAdapter$ModeHolder$$Lambda$1(AbstractQBChooseModeListFragment.SelectModeAdapter.ModeHolder modeHolder, AbstractQBChooseModeListFragment.OperationModel operationModel, int n) {
        this.arg$1 = modeHolder;
        this.arg$2 = operationModel;
        this.arg$3 = n;
    }

    public static View.OnClickListener lambdaFactory$(AbstractQBChooseModeListFragment.SelectModeAdapter.ModeHolder modeHolder, AbstractQBChooseModeListFragment.OperationModel operationModel, int n) {
        return new AbstractQBChooseModeListFragment$SelectModeAdapter$ModeHolder$$Lambda$1(modeHolder, operationModel, n);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$bindModelWithUI$0(this.arg$2, this.arg$3, view);
    }
}

