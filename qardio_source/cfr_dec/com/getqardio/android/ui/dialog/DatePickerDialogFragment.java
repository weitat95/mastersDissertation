/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package com.getqardio.android.ui.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

public class DatePickerDialogFragment
extends DialogFragment {
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public static DatePickerDialogFragment newInstance(int n, int n2, int n3) {
        Bundle bundle = new Bundle(3);
        bundle.putInt("com.getqardio.android.argument.YEAR", n);
        bundle.putInt("com.getqardio.android.argument.MONTH", n2);
        bundle.putInt("com.getqardio.android.argument.DAY", n3);
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setArguments(bundle);
        return datePickerDialogFragment;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        bundle = this.getArguments();
        int n = bundle.getInt("com.getqardio.android.argument.YEAR");
        int n2 = bundle.getInt("com.getqardio.android.argument.MONTH");
        int n3 = bundle.getInt("com.getqardio.android.argument.DAY");
        if (Build.VERSION.SDK_INT >= 21) {
            return new DatePickerDialog((Context)this.getActivity(), 2131493368, this.dateSetListener, n, n2, n3);
        }
        return new DatePickerDialog((Context)this.getActivity(), this.dateSetListener, n, n2, n3);
    }

    public void onDetach() {
        super.onDetach();
        this.dateSetListener = null;
    }

    public void setDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.dateSetListener = onDateSetListener;
    }
}

