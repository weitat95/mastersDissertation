/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.getqardio.android.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.getqardio.android.ui.dialog.WeightPickerDialog;

public class WeightPickerFragment
extends DialogFragment
implements WeightPickerDialog.OnWeightSelectedListener {
    public static WeightPickerFragment newInstance() {
        return new WeightPickerFragment();
    }

    public static WeightPickerFragment newInstance(float f, int n, boolean bl) {
        Bundle bundle;
        WeightPickerFragment weightPickerFragment = WeightPickerFragment.newInstance();
        Bundle bundle2 = bundle = weightPickerFragment.getArguments();
        if (bundle == null) {
            bundle2 = new Bundle(3);
        }
        bundle2.putFloat("ARG_INITIAL_WEIGHT", f);
        bundle2.putInt("ARG_INITIAL_WEIGHT_UNIT", n);
        bundle2.putBoolean("ARG_CAN_CHANGE_WEIGHT_UNIT", bl);
        weightPickerFragment.setArguments(bundle2);
        return weightPickerFragment;
    }

    public void onCancel(DialogInterface dialogInterface) {
        Fragment fragment = this.getTargetFragment();
        int n = this.getTargetRequestCode();
        if (fragment != null) {
            fragment.onActivityResult(n, 0, null);
        }
        super.onCancel(dialogInterface);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public Dialog onCreateDialog(Bundle object) {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("ARG_INITIAL_WEIGHT") && bundle.containsKey("ARG_INITIAL_WEIGHT_UNIT")) {
            void var1_4;
            float f = this.getArguments().getFloat("ARG_INITIAL_WEIGHT", 0.0f);
            int n = this.getArguments().getInt("ARG_INITIAL_WEIGHT_UNIT", 0);
            if (this.getArguments().getBoolean("ARG_CAN_CHANGE_WEIGHT_UNIT", true)) {
                WeightPickerDialog weightPickerDialog = new WeightPickerDialog((Context)this.getActivity(), this);
            } else {
                WeightPickerDialog weightPickerDialog = new WeightPickerDialog((Context)this.getActivity(), n, this);
            }
            var1_4.setWeight(n, f);
            return var1_4;
        }
        return new WeightPickerDialog((Context)this.getActivity(), this);
    }

    @Override
    public void onWeightSelected(float f, int n) {
        Fragment fragment = this.getTargetFragment();
        int n2 = this.getTargetRequestCode();
        Intent intent = new Intent();
        intent.putExtra("EXTRA_RESULT_WEIGHT", f);
        intent.putExtra("EXTRA_RESULT_WEIGHT_UNIT", n);
        if (fragment != null) {
            fragment.onActivityResult(n2, -1, intent);
        }
    }
}

