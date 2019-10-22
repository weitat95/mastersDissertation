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

final class PermissionUtil$BlePermissions$$Lambda$2
implements DialogInterface.OnClickListener {
    private final Activity arg$1;

    private PermissionUtil$BlePermissions$$Lambda$2(Activity activity) {
        this.arg$1 = activity;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(Activity activity) {
        return new PermissionUtil$BlePermissions$$Lambda$2(activity);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        PermissionUtil.BlePermissions.lambda$checkFineLocationPermission$1(this.arg$1, dialogInterface, n);
    }
}

