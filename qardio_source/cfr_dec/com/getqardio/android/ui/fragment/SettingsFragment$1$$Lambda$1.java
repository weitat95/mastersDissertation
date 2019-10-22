/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SettingsFragment;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$1$$Lambda$1
implements View.OnClickListener {
    private final SettingsFragment.1 arg$1;
    private final String arg$2;
    private final String arg$3;
    private final String arg$4;

    private SettingsFragment$1$$Lambda$1(SettingsFragment.1 var1_1, String string2, String string3, String string4) {
        this.arg$1 = var1_1;
        this.arg$2 = string2;
        this.arg$3 = string3;
        this.arg$4 = string4;
    }

    public static View.OnClickListener lambdaFactory$(SettingsFragment.1 var0, String string2, String string3, String string4) {
        return new SettingsFragment$1$$Lambda$1(var0, string2, string3, string4);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onReceive$0(this.arg$2, this.arg$3, this.arg$4, view);
    }
}

