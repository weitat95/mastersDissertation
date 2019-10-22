/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.Convert;

public class ReminderEditFragment
extends Fragment {
    private ReminderEditFragmentCallback callback;
    private TextView repeatValueText;
    private View repeatView;
    private View rootView;
    private MenuItem saveButton;
    private TextView timeValueText;
    private View timeView;

    private void fillDaysText(Reminder object) {
        object = Convert.Reminder.daysToString((Context)this.getActivity(), ((Reminder)object).days);
        this.repeatValueText.setText((CharSequence)object);
    }

    private void fillTimeText(Reminder object) {
        object = Convert.Reminder.localTimeToTimeString(Convert.Reminder.timeAfterMidnightToLocalTime(((Reminder)object).remindTime));
        this.timeValueText.setText((CharSequence)object);
    }

    public static ReminderEditFragment newInstance() {
        return new ReminderEditFragment();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.setHasOptionsMenu(true);
        ActivityUtils.getActionBar(this.getActivity()).setDisplayHomeAsUpEnabled(true);
        this.timeValueText = (TextView)this.rootView.findViewById(2131821268);
        this.repeatValueText = (TextView)this.rootView.findViewById(2131821271);
        this.timeView = this.rootView.findViewById(2131821172);
        this.timeView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ReminderEditFragment.this.callback.showTimePickerDialog();
            }
        });
        this.repeatView = this.rootView.findViewById(2131821269);
        this.repeatView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ReminderEditFragment.this.callback.displayDaysReminderFragment();
            }
        });
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (ReminderEditFragmentCallback)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            return;
        }
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886094, menu2);
        this.saveButton = menu2.findItem(2131821493);
        this.saveButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            public boolean onMenuItemClick(MenuItem menuItem) {
                ReminderEditFragment.this.callback.saveReminder();
                return true;
            }
        });
        this.refresh();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968806, viewGroup, false);
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.getActivity().onBackPressed();
        return true;
    }

    public void onResume() {
        super.onResume();
        this.refresh();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void refresh() {
        boolean bl = true;
        Reminder reminder = this.callback.getReminder();
        this.fillTimeText(reminder);
        this.fillDaysText(reminder);
        if (this.saveButton != null) {
            boolean bl2 = reminder.days != 0;
            if (!bl2) {
                this.repeatValueText.setError((CharSequence)this.getString(2131362044));
                this.repeatValueText.requestFocus();
            } else {
                this.repeatValueText.setError(null);
            }
            boolean bl3 = this.callback.wasReminderUpdated();
            reminder = this.saveButton;
            if (!bl3 || !bl2) {
                bl = false;
            }
            reminder.setEnabled(bl);
        }
    }

    public static interface ReminderEditFragmentCallback {
        public void displayDaysReminderFragment();

        public Reminder getReminder();

        public void saveReminder();

        public void showTimePickerDialog();

        public boolean wasReminderUpdated();
    }

}

