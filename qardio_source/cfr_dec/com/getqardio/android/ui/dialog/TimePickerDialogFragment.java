/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.app.TimePickerDialog
 *  android.app.TimePickerDialog$OnTimeSetListener
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package com.getqardio.android.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

public class TimePickerDialogFragment
extends DialogFragment {
    private TimePickerDialog.OnTimeSetListener listener;

    public static TimePickerDialogFragment newInstance(int n, int n2, boolean bl) {
        TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("com.getqardio.android.extras.HOUR", n);
        bundle.putInt("com.getqardio.android.extras.MINUTE", n2);
        bundle.putBoolean("com.getqardio.android.extras.24HOUR", bl);
        timePickerDialogFragment.setArguments(bundle);
        return timePickerDialogFragment;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        bundle = this.getArguments();
        int n = bundle.getInt("com.getqardio.android.extras.HOUR");
        int n2 = bundle.getInt("com.getqardio.android.extras.MINUTE");
        boolean bl = bundle.getBoolean("com.getqardio.android.extras.24HOUR");
        if (Build.VERSION.SDK_INT >= 21) {
            return new TimePickerDialog((Context)this.getActivity(), 2131493020, this.listener, n, n2, bl);
        }
        return new TimePickerDialog((Context)this.getActivity(), this.listener, n, n2, bl);
    }

    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public void setTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.listener = onTimeSetListener;
    }
}

