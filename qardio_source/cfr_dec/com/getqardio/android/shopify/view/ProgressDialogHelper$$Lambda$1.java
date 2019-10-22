/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 */
package com.getqardio.android.shopify.view;

import android.content.DialogInterface;
import com.getqardio.android.shopify.view.ProgressDialogHelper;
import java.lang.invoke.LambdaForm;

final class ProgressDialogHelper$$Lambda$1
implements DialogInterface.OnCancelListener {
    private final Runnable arg$1;

    private ProgressDialogHelper$$Lambda$1(Runnable runnable) {
        this.arg$1 = runnable;
    }

    public static DialogInterface.OnCancelListener lambdaFactory$(Runnable runnable) {
        return new ProgressDialogHelper$$Lambda$1(runnable);
    }

    @LambdaForm.Hidden
    public void onCancel(DialogInterface dialogInterface) {
        ProgressDialogHelper.lambda$show$0(this.arg$1, dialogInterface);
    }
}

