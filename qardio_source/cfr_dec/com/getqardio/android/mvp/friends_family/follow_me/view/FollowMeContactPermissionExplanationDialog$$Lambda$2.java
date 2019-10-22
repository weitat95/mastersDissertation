/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.content.DialogInterface;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeContactPermissionExplanationDialog;
import java.lang.invoke.LambdaForm;

final class FollowMeContactPermissionExplanationDialog$$Lambda$2
implements DialogInterface.OnClickListener {
    private final FollowMeContactPermissionExplanationDialog arg$1;

    private FollowMeContactPermissionExplanationDialog$$Lambda$2(FollowMeContactPermissionExplanationDialog followMeContactPermissionExplanationDialog) {
        this.arg$1 = followMeContactPermissionExplanationDialog;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(FollowMeContactPermissionExplanationDialog followMeContactPermissionExplanationDialog) {
        return new FollowMeContactPermissionExplanationDialog$$Lambda$2(followMeContactPermissionExplanationDialog);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$onCreateDialog$1(dialogInterface, n);
    }
}

