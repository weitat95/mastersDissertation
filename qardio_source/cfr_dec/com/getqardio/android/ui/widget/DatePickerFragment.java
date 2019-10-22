/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.widget.DatePicker
 */
package com.getqardio.android.ui.widget;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import com.getqardio.android.utils.DateUtils;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment
extends DialogFragment
implements DatePickerDialog.OnDateSetListener {
    private Date maxDate;
    private Date minDate;

    public static DatePickerFragment newInstance(Date date, Date date2, Date date3) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle bundle = new Bundle(3);
        if (date != null) {
            bundle.putLong("ARG_DEFAULT_DATE", date.getTime());
        }
        if (date2 != null) {
            bundle.putLong("ARG_MIN_DATE", DateUtils.getStartOfTheDay(date2).getTime());
        }
        if (date3 != null) {
            bundle.putLong("ARG_MAX_DATE", DateUtils.getEndOfTheDay(date3).getTime());
        }
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    public void onCancel(DialogInterface dialogInterface) {
        Fragment fragment = this.getTargetFragment();
        int n = this.getTargetRequestCode();
        if (fragment != null) {
            fragment.onActivityResult(n, 0, null);
        }
        super.onCancel(dialogInterface);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() != null) {
            if (this.getArguments().containsKey("ARG_MIN_DATE")) {
                this.minDate = new Date(this.getArguments().getLong("ARG_MIN_DATE"));
            }
            if (this.getArguments().containsKey("ARG_MAX_DATE")) {
                this.maxDate = new Date(this.getArguments().getLong("ARG_MAX_DATE"));
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public Dialog onCreateDialog(Bundle object) {
        void var1_3;
        void var1_5;
        if (this.getArguments().containsKey("ARG_DEFAULT_DATE")) {
            Date date = new Date(this.getArguments().getLong("ARG_DEFAULT_DATE"));
        } else {
            Date date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date)var1_3);
        int n = calendar.get(1);
        int n2 = calendar.get(2);
        int n3 = calendar.get(5);
        if (Build.VERSION.SDK_INT >= 21) {
            DatePickerDialog datePickerDialog = new DatePickerDialog((Context)this.getActivity(), 2131493368, (DatePickerDialog.OnDateSetListener)this, n, n2, n3);
        } else {
            DatePickerDialog datePickerDialog = new DatePickerDialog((Context)this.getActivity(), (DatePickerDialog.OnDateSetListener)this, n, n2, n3);
        }
        if (this.minDate != null) {
            var1_5.getDatePicker().setMinDate(this.minDate.getTime());
        }
        if (this.maxDate != null) {
            var1_5.getDatePicker().setMaxDate(this.maxDate.getTime());
        }
        return var1_5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDateSet(DatePicker object, int n, int n2, int n3) {
        object = Calendar.getInstance();
        ((Calendar)object).set(n, n2, n3);
        if (this.minDate != null && ((Calendar)object).getTime().before(this.minDate)) {
            ((Calendar)object).setTime(this.minDate);
        } else if (this.maxDate != null && ((Calendar)object).getTime().after(this.maxDate)) {
            ((Calendar)object).setTime(this.maxDate);
        }
        Fragment fragment = this.getTargetFragment();
        n = this.getTargetRequestCode();
        Intent intent = new Intent();
        intent.putExtra("EXTRA_RESULT_DATE_IN_MILLIS", ((Calendar)object).getTimeInMillis());
        if (fragment != null) {
            fragment.onActivityResult(n, -1, intent);
        }
    }
}

