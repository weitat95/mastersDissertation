/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.utils.permission;

import android.app.Activity;
import android.content.DialogInterface;
import com.getqardio.android.utils.permission.PermissionUtil;
import java.lang.invoke.LambdaForm;

final class PermissionUtil$Contact$$Lambda$1
implements DialogInterface.OnClickListener {
    private final Activity arg$1;

    private PermissionUtil$Contact$$Lambda$1(Activity activity) {
        this.arg$1 = activity;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(Activity activity) {
        return new PermissionUtil$Contact$$Lambda$1(activity);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        PermissionUtil.Contact.lambda$checkReadContactsPermission$0(this.arg$1, dialogInterface, n);
    }
}

