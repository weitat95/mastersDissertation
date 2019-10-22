/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.Intent
 *  android.content.Loader
 *  android.database.Cursor
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.activity.InviteUserActivity;
import com.getqardio.android.ui.activity.ReminderEditActivity;
import com.getqardio.android.ui.activity.TooManyRemindersActivity;
import com.getqardio.android.ui.adapter.ReminderListAdapter;
import com.getqardio.android.ui.fragment.ReminderListFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.notifications.AppNotificationAssistant;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;

public class ReminderListFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private ReminderListAdapter adapter;
    private ListView reminderList;
    private int remindersCount = 0;

    public static ReminderListFragment getInstance(String object) {
        Bundle bundle = new Bundle(1);
        bundle.putString("com.getqardio.android.argument.MEASUREMENT_TYPE", object);
        object = new ReminderListFragment();
        object.setArguments(bundle);
        return object;
    }

    private String getMeasurementType() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.MEASUREMENT_TYPE")) {
            return bundle.getString("com.getqardio.android.argument.MEASUREMENT_TYPE");
        }
        return "bp";
    }

    private void init(View view) {
        this.reminderList = (ListView)view.findViewById(2131821266);
        this.adapter = new ReminderListAdapter((Context)this.getActivity(), new ReminderListAdapter.OnReminderDeleteListener(){

            @Override
            public void onDeleteReminder(Reminder reminder) {
                DataHelper.ReminderHelper.deleteReminder((Context)ReminderListFragment.this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), reminder);
            }
        });
        this.reminderList.setAdapter((ListAdapter)this.adapter);
        new BackPanelListViewHelper(this.reminderList).setCallbacks(new BackPanelListViewHelper.BackPanelCallbacks(){

            @Override
            public boolean hasBackPanel(int n) {
                return true;
            }
        });
        this.reminderList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                ReminderListFragment.this.displayEditReminderFragment(l);
            }
        });
    }

    private void trackScreen() {
        AnalyticsScreenTracker.sendScreenWithMeasurementType((Context)this.getActivity(), "Reminders", this.getMeasurementType());
    }

    public void displayCreateReminderFragment() {
        if (this.remindersCount < 10) {
            this.startActivity(ReminderEditActivity.getStartIntent((Context)this.getActivity(), this.getMeasurementType()));
            this.getActivity().overridePendingTransition(2131034132, 2131034131);
            return;
        }
        this.startActivity(TooManyRemindersActivity.getStartIntent((Context)this.getActivity()));
    }

    public void displayEditReminderFragment(long l) {
        this.startActivity(ReminderEditActivity.getStartEditIntent((Context)this.getActivity(), l, this.getMeasurementType()));
        this.getActivity().overridePendingTransition(2131034132, 2131034131);
    }

    /* synthetic */ void lambda$onResume$0(View view) {
        MvpApplication.get((Context)this.getActivity()).getNotificationAssistant().openNotificationChannelSetting("reminders_channel_id");
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362036);
        this.trackScreen();
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        DataHelper.ReminderHelper.requestReminderUpdate((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), new String[]{this.getMeasurementType()});
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setHasOptionsMenu(true);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return DataHelper.ReminderHelper.getRemindersLoader((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), this.getMeasurementType(), null);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886093, menu2);
        if ("bp".equals(this.getMeasurementType())) {
            menu2.removeItem(2131821492);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968808, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: {
                this.remindersCount = cursor.getCount();
                if (this.remindersCount == 0) {
                    this.getActivity().findViewById(2131821273).setVisibility(4);
                    this.getActivity().findViewById(2131821272).setVisibility(0);
                    this.getActivity().findViewById(2131821266).setVisibility(4);
                } else {
                    this.getActivity().findViewById(2131821273).setVisibility(4);
                    this.getActivity().findViewById(2131821272).setVisibility(4);
                    this.getActivity().findViewById(2131821266).setVisibility(0);
                    this.adapter.swapCursor(cursor);
                }
                if (Build.VERSION.SDK_INT < 26 || AppNotificationAssistant.getInstance((Context)this.getActivity()).checkChannelEnabled("reminders_channel_id")) return;
                this.getActivity().findViewById(2131821273).setVisibility(0);
                this.getActivity().findViewById(2131821272).setVisibility(4);
                this.getActivity().findViewById(2131821266).setVisibility(4);
                return;
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.adapter.swapCursor(null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                do {
                    return super.onOptionsItemSelected(menuItem);
                    break;
                } while (true);
            }
            case 2131821491: {
                this.displayCreateReminderFragment();
                return true;
            }
            case 16908332: {
                this.getActivity().onBackPressed();
                return true;
            }
            case 2131821492: 
        }
        this.startActivity(new Intent((Context)this.getActivity(), InviteUserActivity.class));
        return super.onOptionsItemSelected(menuItem);
    }

    public void onResume() {
        block5: {
            block4: {
                super.onResume();
                if (Build.VERSION.SDK_INT < 26) break block4;
                this.getActivity().findViewById(2131821277).setOnClickListener(ReminderListFragment$$Lambda$1.lambdaFactory$(this));
                if (MvpApplication.get((Context)this.getActivity()).getNotificationAssistant().checkChannelEnabled("reminders_channel_id")) break block5;
                this.getActivity().findViewById(2131821273).setVisibility(0);
                this.getActivity().findViewById(2131821266).setVisibility(4);
                this.getActivity().findViewById(2131821272).setVisibility(4);
            }
            return;
        }
        this.getActivity().findViewById(2131821273).setVisibility(8);
        if (this.adapter.getCount() == 0) {
            this.getActivity().findViewById(2131821266).setVisibility(4);
            this.getActivity().findViewById(2131821272).setVisibility(0);
            return;
        }
        this.getActivity().findViewById(2131821266).setVisibility(0);
        this.getActivity().findViewById(2131821272).setVisibility(4);
    }

}

