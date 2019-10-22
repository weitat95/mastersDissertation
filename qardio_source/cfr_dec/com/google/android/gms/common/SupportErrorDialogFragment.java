/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 */
package com.google.android.gms.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import com.google.android.gms.common.internal.zzbq;

public class SupportErrorDialogFragment
extends DialogFragment {
    private Dialog mDialog = null;
    private DialogInterface.OnCancelListener zzfkt = null;

    public static SupportErrorDialogFragment newInstance(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        SupportErrorDialogFragment supportErrorDialogFragment = new SupportErrorDialogFragment();
        dialog = zzbq.checkNotNull(dialog, "Cannot display null dialog");
        dialog.setOnCancelListener(null);
        dialog.setOnDismissListener(null);
        supportErrorDialogFragment.mDialog = dialog;
        if (onCancelListener != null) {
            supportErrorDialogFragment.zzfkt = onCancelListener;
        }
        return supportErrorDialogFragment;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (this.zzfkt != null) {
            this.zzfkt.onCancel(dialogInterface);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        if (this.mDialog == null) {
            this.setShowsDialog(false);
        }
        return this.mDialog;
    }

    @Override
    public void show(FragmentManager fragmentManager, String string2) {
        super.show(fragmentManager, string2);
    }
}

