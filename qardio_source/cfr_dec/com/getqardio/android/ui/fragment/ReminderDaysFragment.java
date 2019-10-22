/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.ui.adapter.ReminderDayListAdapter;
import com.getqardio.android.utils.ui.ActivityUtils;
import java.util.Arrays;
import java.util.List;

public class ReminderDaysFragment
extends Fragment
implements ReminderDayListAdapter.ReminderDayListAdapterCallback {
    private ReminderDayListAdapter adapter;
    private ReminderDaysFragmentCallback callback;
    private List<Integer> dayOfWeekArray;
    private ListView reminderList;
    private View rootView;

    public static ReminderDaysFragment newInstance() {
        return new ReminderDaysFragment();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void dayCheckChanged(int n) {
        boolean bl = !this.isDaySelected(n);
        Reminder reminder = this.callback.getReminder();
        n = bl ? reminder.days | n : reminder.days & ~n;
        reminder.days = n;
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public boolean isDaySelected(int n) {
        return (this.callback.getReminder().days & n) == n;
    }

    public void onActivityCreated(Bundle object) {
        super.onActivityCreated((Bundle)object);
        this.setHasOptionsMenu(true);
        object = ActivityUtils.getActionBar(this.getActivity());
        ((ActionBar)object).setDisplayHomeAsUpEnabled(true);
        ((ActionBar)object).setTitle(2131362037);
        this.reminderList = (ListView)this.rootView.findViewById(2131821266);
        this.dayOfWeekArray = Arrays.asList(127, 1, 2, 4, 8, 16, 32, 64);
        this.adapter = new ReminderDayListAdapter((Context)this.getActivity(), this.dayOfWeekArray, this);
        this.reminderList.setAdapter((ListAdapter)this.adapter);
        this.reminderList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                n = (Integer)ReminderDaysFragment.this.dayOfWeekArray.get(n);
                ReminderDaysFragment.this.dayCheckChanged(n);
            }
        });
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (ReminderDaysFragmentCallback)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            return;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968803, viewGroup, false);
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
        this.getFragmentManager().popBackStack();
        return true;
    }

    public static interface ReminderDaysFragmentCallback {
        public Reminder getReminder();
    }

}

