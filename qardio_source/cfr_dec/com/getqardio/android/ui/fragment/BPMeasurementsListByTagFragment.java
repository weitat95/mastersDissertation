/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.Loader
 *  android.database.Cursor
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.adapter.BPMeasurementsListAdapter;
import com.getqardio.android.ui.adapter.DateTimeFormatHelper;

public class BPMeasurementsListByTagFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private DateTimeFormatHelper dateTimeFormatHelper;
    private BPMeasurementsListAdapter listAdapter;

    public static BPMeasurementsListByTagFragment getInstance(long l, int n) {
        Bundle bundle = new Bundle(2);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putInt("com.getqardio.android.argument.MEASUREMENTS_TAG", n);
        BPMeasurementsListByTagFragment bPMeasurementsListByTagFragment = new BPMeasurementsListByTagFragment();
        bPMeasurementsListByTagFragment.setArguments(bundle);
        return bPMeasurementsListByTagFragment;
    }

    private int getMeasurementsTag() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.MEASUREMENTS_TAG")) {
            return bundle.getInt("com.getqardio.android.argument.MEASUREMENTS_TAG");
        }
        return 0;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void init(ListView listView) {
        listView.setSelector((Drawable)new ColorDrawable(0));
        this.listAdapter = new BPMeasurementsListAdapter((Context)this.getActivity(), true);
        listView.setAdapter((ListAdapter)this.listAdapter);
        this.dateTimeFormatHelper = new DateTimeFormatHelper(this.listAdapter);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return MeasurementHelper.BloodPressure.getMeasurementsByTagLoader((Context)this.getActivity(), this.getUserId(), this.getMeasurementsTag(), MeasurementHelper.BloodPressure.MEASUREMENTS_LIST_PROJECTION);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = (ListView)layoutInflater.inflate(2130968646, viewGroup, false);
        this.init((ListView)layoutInflater);
        return layoutInflater;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.listAdapter.swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.listAdapter.swapCursor(null);
    }

    public void onResume() {
        super.onResume();
        this.dateTimeFormatHelper.onUpdatePatterns();
    }
}

