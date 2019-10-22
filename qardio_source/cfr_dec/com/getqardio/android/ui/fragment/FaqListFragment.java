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
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
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
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.adapter.FaqListAdapter;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import timber.log.Timber;

public class FaqListFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private FaqListAdapter adapter;
    private Callback callback;

    private void init(View view) {
        this.adapter = new FaqListAdapter((Context)this.getActivity());
        view = (ListView)view.findViewById(2131821049);
        view.setAdapter((ListAdapter)this.adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                if (FaqListFragment.this.callback != null) {
                    FaqListFragment.this.callback.displayFAQDetailsFragment(l);
                }
            }
        });
    }

    public static FaqListFragment newInstance() {
        return new FaqListFragment();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.setHasOptionsMenu(true);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362062);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Help");
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (Callback)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            Timber.e(classCastException, "Host activity for FaqListFragment should implements FaqListFragment.OnFaqItemSelectedListener", new Object[0]);
            return;
        }
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return DataHelper.FaqHelper.getFaqListLoader((Context)this.getActivity(), DataHelper.FaqHelper.FAQ_LIST_PROJECTION);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968706, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.adapter.swapCursor(cursor);
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

    public static interface Callback {
        public void displayFAQDetailsFragment(long var1);
    }

}

