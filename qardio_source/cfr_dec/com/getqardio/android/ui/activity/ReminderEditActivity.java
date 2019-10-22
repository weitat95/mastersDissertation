/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentManager$OnBackStackChangedListener
 *  android.app.FragmentTransaction
 *  android.app.TimePickerDialog
 *  android.app.TimePickerDialog$OnTimeSetListener
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.format.DateFormat
 *  android.widget.TimePicker
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.dialog.TimePickerDialogFragment;
import com.getqardio.android.ui.fragment.ReminderDaysFragment;
import com.getqardio.android.ui.fragment.ReminderEditFragment;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.Convert;
import java.util.Calendar;

public class ReminderEditActivity
extends BaseActivity
implements FragmentManager.OnBackStackChangedListener,
TimePickerDialog.OnTimeSetListener,
ReminderDaysFragment.ReminderDaysFragmentCallback,
ReminderEditFragment.ReminderEditFragmentCallback {
    private boolean isInEditMode;
    private Reminder reminder;
    private int reminderOriginalHash;

    private Reminder createDefaultReminder(String string2) {
        Reminder reminder = new Reminder();
        this.reminderOriginalHash = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.add(12, 1);
        reminder.remindTime = Convert.Reminder.localTimeToTimeAfterMidnight(calendar.getTimeInMillis());
        reminder.repeat = true;
        reminder.type = string2;
        reminder.days = 127;
        return reminder;
    }

    public static Intent getStartEditIntent(Context context, long l, String string2) {
        context = new Intent(context, ReminderEditActivity.class);
        context.putExtra("extra_key_id", l);
        context.putExtra("extra_measurement_type", string2);
        return context;
    }

    public static Intent getStartIntent(Context context, String string2) {
        context = new Intent(context, ReminderEditActivity.class);
        context.putExtra("extra_measurement_type", string2);
        return context;
    }

    private void initReminderEditFragment(boolean bl) {
        if (this.getFragmentManager().findFragmentByTag("reminder_edit_fragment_tag") == null) {
            this.getFragmentManager().beginTransaction().add(2131821020, (Fragment)ReminderEditFragment.newInstance(), "reminder_edit_fragment_tag").commit();
        }
        this.setAddReminderTitle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setAddReminderTitle() {
        ActionBar actionBar = ActivityUtils.getActionBar(this);
        int n = this.isInEditMode ? 2131361909 : 2131361844;
        actionBar.setTitle(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void trackScreen(boolean bl) {
        String string2 = bl ? "Edit reminder" : "Add reminder";
        AnalyticsScreenTracker.sendScreenWithMeasurementType((Context)this, string2, this.getIntent().getStringExtra("extra_measurement_type"));
    }

    @Override
    public void displayDaysReminderFragment() {
        this.getFragmentManager().beginTransaction().replace(2131821020, (Fragment)ReminderDaysFragment.newInstance(), "reminder_days_fragment_tag").addToBackStack(null).commit();
        ActivityUtils.getActionBar(this).setTitle(2131362037);
    }

    @Override
    public Reminder getReminder() {
        return this.reminder;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.getFragmentManager().removeOnBackStackChangedListener((FragmentManager.OnBackStackChangedListener)this);
        this.overridePendingTransition(2131034130, 2131034133);
    }

    public void onBackStackChanged() {
        if (this.getFragmentManager().getBackStackEntryCount() == 0) {
            this.setAddReminderTitle();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle bundle) {
        String string2;
        super.onCreate(bundle);
        this.setContentView(2130968805);
        String string3 = string2 = "bp";
        if (this.getIntent() != null) {
            string3 = string2;
            if (this.getIntent().hasExtra("extra_measurement_type")) {
                string3 = this.getIntent().getStringExtra("extra_measurement_type");
            }
        }
        if (bundle != null) {
            this.reminder = (Reminder)bundle.getParcelable("reminder_state");
            this.reminderOriginalHash = bundle.getInt("reminder_original_hash", 0);
            this.isInEditMode = bundle.getBoolean("edit_reminder_mode", false);
        } else if (this.reminder == null) {
            long l = this.getIntent().getLongExtra("extra_key_id", -1L);
            if (l == -1L) {
                this.reminder = this.createDefaultReminder(string3);
            } else {
                this.isInEditMode = true;
                this.reminder = DataHelper.ReminderHelper.getReminderById((Context)this, CustomApplication.getApplication().getCurrentUserId(), l);
                if (this.reminder == null) {
                    this.reminder = this.createDefaultReminder(string3);
                }
                this.reminderOriginalHash = this.reminder.hashCode();
            }
        }
        this.getFragmentManager().addOnBackStackChangedListener((FragmentManager.OnBackStackChangedListener)this);
        this.initReminderEditFragment(this.isInEditMode);
        this.trackScreen(this.isInEditMode);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("reminder_state", (Parcelable)this.reminder);
        bundle.putBoolean("edit_reminder_mode", this.isInEditMode);
        bundle.putInt("reminder_original_hash", this.reminderOriginalHash);
    }

    public void onTimeSet(TimePicker object, int n, int n2) {
        this.reminder.remindTime = Convert.Reminder.getTimeAfterMidnight(n, n2);
        object = (ReminderEditFragment)this.getFragmentManager().findFragmentByTag("reminder_edit_fragment_tag");
        if (object != null) {
            object.refresh();
        }
    }

    @Override
    public void saveReminder() {
        DataHelper.ReminderHelper.saveReminder((Context)this, CustomApplication.getApplication().getCurrentUserId(), this.reminder);
        this.finish();
    }

    @Override
    public void showTimePickerDialog() {
        TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(Convert.Reminder.timeAfterMidnightToLocalHours(this.reminder.remindTime), Convert.Reminder.timeAfterMidnightToLocalMinutes(this.reminder.remindTime), DateFormat.is24HourFormat((Context)this));
        timePickerDialogFragment.setTimeSetListener(this);
        timePickerDialogFragment.show(this.getFragmentManager(), null);
    }

    @Override
    public boolean wasReminderUpdated() {
        return this.reminder.hashCode() != this.reminderOriginalHash;
    }
}

