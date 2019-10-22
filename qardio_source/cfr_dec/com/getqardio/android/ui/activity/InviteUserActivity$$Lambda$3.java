/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.activity;

import android.content.DialogInterface;
import com.getqardio.android.ui.activity.InviteUserActivity;
import java.lang.invoke.LambdaForm;

final class InviteUserActivity$$Lambda$3
implements DialogInterface.OnClickListener {
    private final InviteUserActivity arg$1;

    private InviteUserActivity$$Lambda$3(InviteUserActivity inviteUserActivity) {
        this.arg$1 = inviteUserActivity;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(InviteUserActivity inviteUserActivity) {
        return new InviteUserActivity$$Lambda$3(inviteUserActivity);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$showSuccessDialog$2(dialogInterface, n);
    }
}

