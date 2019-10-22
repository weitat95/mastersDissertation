/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class ProgressDialogFragment
extends DialogFragment {
    private ProgressDialog progressDialog;

    public static ProgressDialogFragment newInstance(boolean bl) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("cancelable", bl);
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.setArguments(bundle);
        return progressDialogFragment;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        this.getArguments().getString("message");
        boolean bl = this.getArguments().getBoolean("cancelable");
        this.progressDialog = new ProgressDialog((Context)this.getActivity(), 2131493132){

            protected void onCreate(Bundle bundle) {
                super.onCreate(bundle);
                this.setContentView(2130968783);
            }
        };
        this.setCancelable(bl);
        this.setRetainInstance(true);
        return this.progressDialog;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (this.isCancelable() && this.getActivity() instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener)this.getActivity()).onDismiss(dialogInterface);
        }
    }

}

