/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.app.FragmentManager
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 */
package com.google.android.gms.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.gms.common.internal.zzbq;

public class ErrorDialogFragment
extends DialogFragment {
    private Dialog mDialog = null;
    private DialogInterface.OnCancelListener zzfkt = null;

    public static ErrorDialogFragment newInstance(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        dialog = zzbq.checkNotNull(dialog, "Cannot display null dialog");
        dialog.setOnCancelListener(null);
        dialog.setOnDismissListener(null);
        errorDialogFragment.mDialog = dialog;
        if (onCancelListener != null) {
            errorDialogFragment.zzfkt = onCancelListener;
        }
        return errorDialogFragment;
    }

    public void onCancel(DialogInterface dialogInterface) {
        if (this.zzfkt != null) {
            this.zzfkt.onCancel(dialogInterface);
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        if (this.mDialog == null) {
            this.setShowsDialog(false);
        }
        return this.mDialog;
    }

    public void show(FragmentManager fragmentManager, String string2) {
        super.show(fragmentManager, string2);
    }
}

